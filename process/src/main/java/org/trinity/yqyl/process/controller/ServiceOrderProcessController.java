package org.trinity.yqyl.process.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.object.RelationshipExpression;
import org.trinity.common.exception.IException;
import org.trinity.message.LookupParser;
import org.trinity.message.exception.GeneralErrorMessage;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.process.converter.IObjectConverter.CopyPolicy;
import org.trinity.yqyl.common.message.dto.domain.AccountBalanceDto;
import org.trinity.yqyl.common.message.dto.domain.AccountPostingDto;
import org.trinity.yqyl.common.message.dto.domain.AccountTransactionDto;
import org.trinity.yqyl.common.message.dto.domain.PaymentDto;
import org.trinity.yqyl.common.message.dto.domain.PosTxDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderOperationDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderSearchingDto;
import org.trinity.yqyl.common.message.exception.ErrorMessage;
import org.trinity.yqyl.common.message.lookup.AccessRight;
import org.trinity.yqyl.common.message.lookup.AccountCategory;
import org.trinity.yqyl.common.message.lookup.FlagStatus;
import org.trinity.yqyl.common.message.lookup.OrderOperation;
import org.trinity.yqyl.common.message.lookup.OrderStatus;
import org.trinity.yqyl.common.message.lookup.PaymentMethod;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.common.message.lookup.ServiceOrderRequirementStatus;
import org.trinity.yqyl.common.message.lookup.TransactionType;
import org.trinity.yqyl.common.utils.YqylUtil;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.IAccountTransactionProcessController;
import org.trinity.yqyl.process.controller.base.IPosProcessController;
import org.trinity.yqyl.process.controller.base.IServiceOrderProcessController;
import org.trinity.yqyl.process.controller.base.IYiquanProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IAccountTransactionRepository;
import org.trinity.yqyl.repository.business.dataaccess.IContentRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceInfoRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceInfoStasticRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceOrderOperationRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceOrderRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceOrderRequirementRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceSupplierStaffRepository;
import org.trinity.yqyl.repository.business.dataaccess.IUserRepository;
import org.trinity.yqyl.repository.business.entity.AccountTransaction;
import org.trinity.yqyl.repository.business.entity.Content;
import org.trinity.yqyl.repository.business.entity.ServiceInfo;
import org.trinity.yqyl.repository.business.entity.ServiceInfoStastic;
import org.trinity.yqyl.repository.business.entity.ServiceOrder;
import org.trinity.yqyl.repository.business.entity.ServiceOrderOperation;
import org.trinity.yqyl.repository.business.entity.ServiceOrderRequirement;
import org.trinity.yqyl.repository.business.entity.User;
import org.trinity.yqyl.repository.business.entity.Yiquan;

@Service
public class ServiceOrderProcessController extends
        AbstractAutowiredCrudProcessController<ServiceOrder, ServiceOrderDto, ServiceOrderSearchingDto, IServiceOrderRepository>
        implements IServiceOrderProcessController {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IServiceInfoRepository serviceInfoRepository;

    @Autowired
    private IObjectConverter<ServiceOrderOperation, ServiceOrderOperationDto> serviceOrderOperationConverter;

    @Autowired
    private IContentRepository contentRepository;

    @Autowired
    private IAccountTransactionProcessController accountTransactionProcessController;

    @Autowired
    private IServiceOrderRequirementRepository serviceOrderRequirementRepository;

    @Autowired
    private IServiceSupplierStaffRepository serviceSupplierStaffRepository;

    @Autowired
    private IServiceOrderOperationRepository serviceOrderOperationRepository;

    @Autowired
    private IServiceInfoStasticRepository serviceInfoStasticRepository;

    @Autowired
    private IYiquanProcessController yiquanProcessController;

    @Autowired
    private IPosProcessController posProcessController;

    @Autowired
    private IAccountTransactionRepository accountTransactionRepository;

    @Override
    @Transactional(rollbackOn = IException.class)
    public void assignOrder(final ServiceOrderDto item) throws IException {
        final ServiceOrder entity = getDomainEntityRepository().findOneByUid(item.getUid());

        if (!entity.getServiceInfo().getServiceSupplierClient().getUser().getUsername().equals(getCurrentUsername())) {
            throw getExceptionFactory().createException(ErrorMessage.INSUFFICIENT_ACCESSRIGHT);
        }

        final List<ServiceOrderOperation> operations = new ArrayList<>();

        final Date now = new Date();

        ServiceOrderOperation operation = new ServiceOrderOperation();
        operation.setOperation(OrderOperation.TAKEN);
        operation.setOperator(getCurrentUsername());
        operation.setOrderStatus(OrderStatus.UNPROCESSED);
        operation.setStatus(RecordStatus.ACTIVE);
        operation.setTimestamp(now);
        operation.setServiceOrder(entity);

        operations.add(operation);

        operation = new ServiceOrderOperation();
        operation.setOperation(OrderOperation.ASSIGNMENT);
        operation.setParams(String.join(",", item.getStaff().getName(), item.getStaff().getPhoneNo()));
        operation.setOperator(getCurrentUsername());
        operation.setOrderStatus(OrderStatus.UNPROCESSED);
        operation.setStatus(RecordStatus.ACTIVE);
        operation.setTimestamp(now);
        operation.setServiceOrder(entity);

        operations.add(operation);

        operation = new ServiceOrderOperation();
        operation.setOperation(OrderOperation.PROCESSING);
        operation.setParams(item.getStaff().getName());
        operation.setOperator(getCurrentUsername());
        operation.setOrderStatus(OrderStatus.UNPROCESSED);
        operation.setStatus(RecordStatus.ACTIVE);
        operation.setTimestamp(now);
        operation.setServiceOrder(entity);

        operations.add(operation);

        if (entity.getPaymentMethod() == PaymentMethod.ONLINE) {
            entity.setStatus(OrderStatus.AWAITING_APPRAISE);
        } else {
            entity.setStatus(OrderStatus.IN_PROGRESS);
        }
        entity.setApprovalTime(now);

        serviceOrderOperationRepository.save(operations);

        entity.setServiceSupplierStaff(serviceSupplierStaffRepository.findOne(item.getStaff().getId()));

        getDomainEntityRepository().save(entity);
    }

    @Override
    @Transactional(rollbackOn = IException.class)
    public ServiceOrderDto cancelOrder(final ServiceOrderDto item) throws IException {
        final ServiceOrder entity = getDomainEntityRepository().findOneByUid(item.getUid());

        final String username = getSecurityUtil().getCurrentToken().getUsername();

        final boolean isSupplier = entity.getServiceInfo().getServiceSupplierClient().getUser().getUsername()
                .equals(username);
        final boolean isReceiver = entity.getUser().getUsername().equals(username);
        final boolean isAdmin = getSecurityUtil().hasAccessRight(AccessRight.ADMINISTRATOR);

        if (!isSupplier && !isReceiver && !isAdmin) {
            throw getExceptionFactory().createException(ErrorMessage.INSUFFICIENT_ACCESSRIGHT);
        }

        boolean statusValidationPassed = true;
        ServiceOrderOperation serviceOrderOperation = new ServiceOrderOperation();

        switch (entity.getStatus()) {
        case UNPROCESSED:
            if (isReceiver) {
                entity.setStatus(OrderStatus.CANCELLED);
                serviceOrderOperation.setOperation(OrderOperation.RECEIVER_CANCELLED);
                serviceOrderOperation.setOrderStatus(OrderStatus.CANCELLED);
                serviceOrderOperation.setParams(item.getOperations().get(0).getParams().get(0));
            } else {
                statusValidationPassed = false;
            }
            break;
        case IN_PROGRESS:
            if (isReceiver) {
                entity.setStatus(OrderStatus.CANCEL_REQUESTED);
                serviceOrderOperation.setOperation(OrderOperation.CANCEL_REQUEST);
                serviceOrderOperation.setOrderStatus(OrderStatus.CANCEL_REQUESTED);
                serviceOrderOperation.setParams(item.getOperations().get(0).getParams().get(0));
            } else {
                statusValidationPassed = false;
            }
            break;
        case CANCEL_REQUESTED:
            if (isSupplier) {
                entity.setStatus(OrderStatus.CANCELLED);
                serviceOrderOperation.setOperation(OrderOperation.SUPPLIER_CANCELLED);
                serviceOrderOperation.setOrderStatus(OrderStatus.CANCELLED);
            } else {
                statusValidationPassed = false;
            }
            break;
        case AWAITING_PAYMENT:
            if (isReceiver && entity.getPaymentMethod() == PaymentMethod.ONLINE) {
                entity.setStatus(OrderStatus.CANCELLED);
                serviceOrderOperation.setOperation(OrderOperation.RECEIVER_CANCELLED);
                serviceOrderOperation.setOrderStatus(OrderStatus.CANCELLED);
                serviceOrderOperation.setParams(item.getOperations().get(0).getParams().get(0));
            } else {
                statusValidationPassed = false;
            }
            break;
        default:
            statusValidationPassed = false;
        }

        if (!statusValidationPassed) {
            if (isAdmin) {
                entity.setStatus(OrderStatus.CANCELLED);
                serviceOrderOperation.setOperation(OrderOperation.ADMIN_CANCELLED);
                serviceOrderOperation.setOrderStatus(OrderStatus.CANCELLED);
            } else {
                throw getExceptionFactory().createException(ErrorMessage.INSUFFICIENT_ACCESSRIGHT);
            }
        }

        try {
            serviceOrderOperation.setOperator(getSecurityUtil().getCurrentToken().getUsername());
        } catch (final IException e) {
        }

        serviceOrderOperation.setServiceOrder(entity);
        serviceOrderOperation.setStatus(RecordStatus.ACTIVE);
        serviceOrderOperation.setTimestamp(new Date());

        serviceOrderOperationRepository.save(serviceOrderOperation);

        final AccountTransaction tx = entity.getPaymentTransaction();
        if (entity.getStatus() == OrderStatus.CANCELLED && tx != null) {
            final AccountTransactionDto reverseTx = new AccountTransactionDto();
            reverseTx.setCode("RV_" + tx.getCode());
            reverseTx.setType(new LookupDto(TransactionType.REVERSE));

            reverseTx.getAccountPostings().addAll(tx.getAccountPostings().stream().map(ap -> {
                final AccountPostingDto reverseAccountPosting = new AccountPostingDto();
                reverseAccountPosting.setAmount(0 - ap.getAmount());
                final AccountBalanceDto reverseBalance = new AccountBalanceDto();
                reverseBalance.setId(ap.getAccountBalance().getId());
                reverseAccountPosting.setBalance(reverseBalance);

                return reverseAccountPosting;
            }).collect(Collectors.toList()));

            entity.setDrawbackTransaction(accountTransactionProcessController.processTransaction(reverseTx));

            serviceOrderOperation = new ServiceOrderOperation();
            serviceOrderOperation.setOperation(OrderOperation.REVERSED);
            serviceOrderOperation.setParams("");
            serviceOrderOperation.setOperator(getSecurityUtil().getCurrentToken().getUsername());
            serviceOrderOperation.setOrderStatus(OrderStatus.UNPROCESSED);
            serviceOrderOperation.setServiceOrder(entity);
            serviceOrderOperation.setStatus(RecordStatus.ACTIVE);
            serviceOrderOperation.setTimestamp(new Date());

            serviceOrderOperationRepository.save(serviceOrderOperation);
        }

        return getDomainObjectConverter().convert(getDomainEntityRepository().save(entity));
    }

    @Override
    @Transactional(rollbackOn = IException.class)
    public List<ServiceOrderDto> changePrice(final List<ServiceOrderDto> data) throws IException {
        final List<ServiceOrder> entities = data.stream().map(item -> {
            final ServiceOrder entity = getDomainEntityRepository().findOneByUid(item.getUid());
            boolean isSupplier = false;
            try {
                isSupplier = entity.getServiceInfo().getServiceSupplierClient().getUser().getUsername()
                        .equals(getSecurityUtil().getCurrentToken().getUsername());
            } catch (final IException e1) {
                return entity;
            }
            if (!isSupplier
                    || entity.getStatus() != OrderStatus.IN_PROGRESS && entity.getStatus() != OrderStatus.UNPROCESSED) {
                if (!getSecurityUtil().hasAccessRight(AccessRight.SUPER_USER)) {
                    return entity;
                }
            }
            final String[] params = new String[] { entity.getExpectedPaymentAmount().toString(),
                    item.getExpectedPaymentAmount().toString() };

            entity.setExpectedPaymentAmount(item.getExpectedPaymentAmount());

            final ServiceOrderOperation serviceOrderOperation = new ServiceOrderOperation();
            serviceOrderOperation.setOperation(OrderOperation.CHANGE_PRICE);
            serviceOrderOperation.setParams(String.join(",", params));
            try {
                serviceOrderOperation.setOperator(getSecurityUtil().getCurrentToken().getUsername());
            } catch (final IException e) {
            }
            serviceOrderOperation.setOrderStatus(entity.getStatus());
            serviceOrderOperation.setServiceOrder(entity);
            serviceOrderOperation.setStatus(RecordStatus.ACTIVE);
            serviceOrderOperation.setTimestamp(new Date());

            serviceOrderOperationRepository.save(serviceOrderOperation);

            return entity;
        }).collect(Collectors.toList());

        return getDomainObjectConverter().convert(getDomainEntityRepository().save(entities));
    }

    @Override
    @Transactional(rollbackOn = IException.class)
    public void confirmOrderRequirement(final ServiceOrderDto serviceOrderDto) throws IException {
        final ServiceOrder order = getDomainEntityRepository().findOneByUid(serviceOrderDto.getUid());

        if (order.getStatus() != OrderStatus.REQUEST_AWAITING_RECEIVER_VEIRFYING) {
            throw getExceptionFactory().createException(ErrorMessage.INCORRECT_SERVICE_ORDER_STATUS);
        }

        if (!order.getServiceInfo().getServiceSupplierClient().getUser().getUsername().equals(getCurrentUsername())
                && !order.getUser().getUsername().equals(getCurrentUsername())) {
            getSecurityUtil().checkAccessRight(AccessRight.ADMINISTRATOR);
        }

        order.setProposalTime(new Date());

        if (order.getPaymentMethod() == PaymentMethod.ONLINE) {
            order.setStatus(OrderStatus.AWAITING_PAYMENT);
        } else {
            order.setStatus(OrderStatus.UNPROCESSED);
        }

        getDomainEntityRepository().save(order);
    }

    @Override
    public int countUnprocessedOrders(final String username) throws IException {
        return getDomainEntityRepository().countUnprocessedOrders(userRepository.findOneByUsername(username),
                new OrderStatus[] { OrderStatus.UNPROCESSED });
    }

    @Override
    @Transactional(rollbackOn = IException.class)
    public void onlinePayment(final PaymentDto payment) throws IException {
        final ServiceOrder order = getDomainEntityRepository().findOneByUid(payment.getOrderUid());

        if (!order.getUser().getUsername().equals(getCurrentUsername())) {
            throw getExceptionFactory().createException(ErrorMessage.INSUFFICIENT_ACCESSRIGHT);
        }

        if (order.getPaymentMethod() != PaymentMethod.ONLINE || order.getStatus() != OrderStatus.AWAITING_PAYMENT) {
            return;
        }

        final String yiquanCode = payment.getYiquanCode();

        final Yiquan yiquan = yiquanProcessController.create(yiquanCode);

        final Double amount = order.getExpectedPaymentAmount();

        order.setActualPaymentAmount(amount);

        posProcessController.payment(yiquanCode, payment.getYiquanPassword(), amount);

        final AccountTransactionDto transaction = new AccountTransactionDto();
        transaction.setType(new LookupDto(TransactionType.ORDER_PAYMENT));

        transaction.setCode(UUID.randomUUID().toString());

        AccountPostingDto accountPosting = new AccountPostingDto();
        AccountBalanceDto accountBalance = new AccountBalanceDto();
        accountBalance.setId(order.getServiceInfo().getServiceSupplierClient().getAccount().getBalances().stream()
                .filter(item -> item.getCategory() == AccountCategory.YIQUAN).findAny().get().getId());
        accountPosting.setBalance(accountBalance);
        accountPosting.setAmount(amount);

        accountPosting = new AccountPostingDto();
        accountBalance = new AccountBalanceDto();
        accountBalance.setId(yiquan.getAccount().getBalances().stream()
                .filter(item -> item.getCategory() == AccountCategory.YIQUAN).findAny().get().getId());
        accountPosting.setBalance(accountBalance);
        accountPosting.setAmount(0 - amount);

        transaction.getAccountPostings().add(accountPosting);

        order.setPaymentTransaction(accountTransactionProcessController.processTransaction(transaction));
        order.setStatus(OrderStatus.UNPROCESSED);

        final ServiceOrderOperation serviceOrderOperation = new ServiceOrderOperation();
        serviceOrderOperation.setOperation(OrderOperation.PAYED);
        serviceOrderOperation.setParams("");
        serviceOrderOperation.setOperator(getSecurityUtil().getCurrentToken().getUsername());
        serviceOrderOperation.setOrderStatus(OrderStatus.UNPROCESSED);
        serviceOrderOperation.setServiceOrder(order);
        serviceOrderOperation.setStatus(RecordStatus.ACTIVE);
        serviceOrderOperation.setTimestamp(new Date());

        serviceOrderOperationRepository.save(serviceOrderOperation);

        getDomainEntityRepository().save(order);
    }

    @Override
    @Transactional(rollbackOn = IException.class)
    public ServiceOrderDto proposeOrder(final ServiceOrderDto serviceOrderDto) throws IException {
        final User user = userRepository.findOneByUsername(getSecurityUtil().getCurrentToken().getUsername());

        if (!StringUtils.isEmpty(serviceOrderDto.getUid())) {
            final ServiceOrder serviceOrder = getDomainEntityRepository().findOneByUid(serviceOrderDto.getUid());
            if (!serviceOrder.getUser().getId().equals(user.getId())) {
                throw getExceptionFactory().createException(ErrorMessage.INVALID_ORDER_ID);
            }

            final ServiceOrderDto newDto = new ServiceOrderDto();
            newDto.setAddress(serviceOrderDto.getAddress());
            newDto.setPhone(serviceOrderDto.getPhone());
            newDto.setServiceDate(serviceOrderDto.getServiceDate());
            newDto.setServiceHour(serviceOrderDto.getServiceHour());

            getDomainObjectConverter().convertBack(newDto, serviceOrder, CopyPolicy.SOURCE_IS_NOT_NULL);

            getDomainEntityRepository().save(serviceOrder);

            final ServiceOrderOperation operation = new ServiceOrderOperation();
            operation.setOperation(OrderOperation.EDIT_BEFORE_PROCESSING);
            operation.setOperator(getSecurityUtil().getCurrentToken().getUsername());
            operation.setOrderStatus(OrderStatus.UNPROCESSED);
            operation.setStatus(RecordStatus.ACTIVE);
            operation.setServiceOrder(serviceOrder);
            operation.setTimestamp(new Date());

            serviceOrderOperationRepository.save(operation);

            return getDomainObjectConverter().convert(serviceOrder);
        } else {
            final ServiceOrder serviceOrder = getDomainObjectConverter().convertBack(serviceOrderDto);
            serviceOrder.setId(null);
            serviceOrder.setUid(YqylUtil.randomServiceOrderId());
            serviceOrder.setPrice(0d);
            serviceOrder.setProposalTime(new Date());
            serviceOrder.setUser(user);

            final ServiceInfo serviceInfo = serviceInfoRepository.findOne(serviceOrderDto.getServiceInfo().getId());

            serviceOrder.setPrice(serviceInfo.getPrice());
            serviceOrder.setExpectedPaymentAmount(serviceInfo.getPrice());
            serviceOrder.setActualPaymentAmount(0d);
            serviceOrder.setPaymentMethod(serviceInfo.getPaymentMethod());
            serviceOrder.setPaymentType(serviceInfo.getPaymentType());
            serviceOrder.setServiceInfo(serviceInfo);

            if (serviceInfo.getPaymentMethod() == PaymentMethod.ONLINE) {
                serviceOrder.setStatus(OrderStatus.AWAITING_PAYMENT);
            } else {
                serviceOrder.setStatus(OrderStatus.UNPROCESSED);
            }

            getDomainEntityRepository().save(serviceOrder);

            if (serviceInfo.getServiceInfoStastic() == null) {
                final ServiceInfoStastic serviceInfoStastic = new ServiceInfoStastic();
                serviceInfoStastic.setServiceInfoId(serviceInfo.getId());
                serviceInfoStastic.setServiceInfo(serviceInfo);
                serviceInfoStastic.setAppraiseAvg(5d);
                serviceInfoStastic.setAppraiseCount(0l);
                serviceInfoStastic.setOrderCount(1l);

                serviceInfoStasticRepository.save(serviceInfoStastic);
            } else {
                serviceInfoStasticRepository.updateForNewOrder(serviceInfo.getId());
            }

            final ServiceOrderOperation operation = new ServiceOrderOperation();
            operation.setOperation(OrderOperation.PROPOSAL);
            operation.setOperator(getSecurityUtil().getCurrentToken().getUsername());
            operation.setOrderStatus(OrderStatus.UNPROCESSED);
            operation.setStatus(RecordStatus.ACTIVE);
            operation.setServiceOrder(serviceOrder);
            operation.setTimestamp(new Date());

            serviceOrderOperationRepository.save(operation);

            return getDomainObjectConverter().convert(serviceOrder);
        }
    }

    @Override
    @Transactional(rollbackOn = IException.class)
    public void proposeOrderRequirement(final ServiceOrderDto serviceOrderDto) throws IException {
        final ServiceOrder order = getDomainEntityRepository().findOneByUid(serviceOrderDto.getUid());

        if (order.getStatus() != OrderStatus.REQUEST_GRABBED) {
            throw getExceptionFactory().createException(ErrorMessage.INCORRECT_SERVICE_ORDER_STATUS);
        }

        final ServiceInfo serviceInfo = serviceInfoRepository.findOne(serviceOrderDto.getServiceInfo().getId());

        if (!order.getServiceInfo().getServiceSupplierClient().getUser().getUsername().equals(getCurrentUsername())
                || !serviceInfo.getServiceSupplierClient().getUser().getUsername().equals(getCurrentUsername())) {
            getSecurityUtil().checkAccessRight(AccessRight.ADMINISTRATOR);
        }

        order.setServiceInfo(serviceInfo);
        order.setExpectedPaymentAmount(serviceOrderDto.getExpectedPaymentAmount());
        order.setPaymentMethod(LookupParser.parse(PaymentMethod.class, serviceOrderDto.getPaymentMethod().getCode()));
        order.setStatus(OrderStatus.REQUEST_AWAITING_RECEIVER_VEIRFYING);

        getDomainEntityRepository().save(order);
    }

    @Override
    @Transactional(rollbackOn = IException.class)
    public List<ServiceOrderDto> rejectCancelOrder(final List<ServiceOrderDto> data) throws IException {
        final List<ServiceOrder> entities = data.stream().map(item -> {
            final ServiceOrder entity = getDomainEntityRepository().findOneByUid(item.getUid());

            String username = null;
            try {
                username = getSecurityUtil().getCurrentToken().getUsername();
            } catch (final IException e1) {
                return entity;
            }

            final boolean isSupplier = entity.getServiceInfo().getServiceSupplierClient().getUser().getUsername()
                    .equals(username);
            final boolean isAdmin = getSecurityUtil().hasAccessRight(AccessRight.ADMINISTRATOR);

            if (!isSupplier && !isAdmin) {
                return entity;
            }

            final ServiceOrderOperation serviceOrderOperation = new ServiceOrderOperation();

            switch (entity.getStatus()) {
            case CANCEL_REQUESTED:
                entity.setStatus(OrderStatus.IN_PROGRESS);
                serviceOrderOperation.setOperation(OrderOperation.CANCEL_REJECTED);
                serviceOrderOperation.setOrderStatus(OrderStatus.IN_PROGRESS);
                break;
            default:
                return entity;
            }

            try {
                serviceOrderOperation.setOperator(getSecurityUtil().getCurrentToken().getUsername());
            } catch (final IException e) {
            }

            serviceOrderOperation.setServiceOrder(entity);
            serviceOrderOperation.setStatus(RecordStatus.ACTIVE);
            serviceOrderOperation.setTimestamp(new Date());

            serviceOrderOperationRepository.save(serviceOrderOperation);

            return entity;
        }).collect(Collectors.toList());

        return getDomainObjectConverter().convert(getDomainEntityRepository().save(entities));
    }

    @Override
    @Transactional(rollbackOn = IException.class)
    public void rejectConfirmOrderRequirement(final ServiceOrderDto serviceOrderDto) throws IException {
        final ServiceOrder order = getDomainEntityRepository().findOneByUid(serviceOrderDto.getUid());

        if (order.getStatus() != OrderStatus.REQUEST_AWAITING_RECEIVER_VEIRFYING) {
            throw getExceptionFactory().createException(ErrorMessage.INCORRECT_SERVICE_ORDER_STATUS);
        }

        if (!order.getServiceInfo().getServiceSupplierClient().getUser().getUsername().equals(getCurrentUsername())
                && !order.getUser().getUsername().equals(getCurrentUsername())) {
            getSecurityUtil().checkAccessRight(AccessRight.ADMINISTRATOR);
        }

        order.setStatus(OrderStatus.REQUEST_GRABBED);

        getDomainEntityRepository().save(order);
    }

    @Override
    @Transactional(rollbackOn = IException.class)
    public void releaseOrder(final List<ServiceOrderDto> data) throws IException {
        for (final ServiceOrderDto serviceOrderDto : data) {
            final ServiceOrder serviceOrder = getDomainEntityRepository().findOneByUid(serviceOrderDto.getUid());

            if (serviceOrder.getStatus() != OrderStatus.REQUEST_GRABBED) {
                throw getExceptionFactory().createException(ErrorMessage.INCORRECT_SERVICE_ORDER_STATUS);
            }
            final ServiceOrderRequirement serviceOrderRequirement = serviceOrder.getServiceOrderRequirement();

            serviceOrder.setStatus(OrderStatus.REQUEST_FAILED);
            if (serviceOrderRequirement != null) {
                serviceOrderRequirement.setStatus(ServiceOrderRequirementStatus.ACTIVE);

                serviceOrderRequirementRepository.save(serviceOrderRequirement);
            }

            serviceOrder.setServiceOrderRequirement(null);

            getDomainEntityRepository().save(serviceOrder);
        }
    }

    @Override
    @Transactional(rollbackOn = IException.class)
    public List<ServiceOrderDto> sendTxCode(final List<ServiceOrderDto> data) throws IException {
        final List<ServiceOrder> entities = new ArrayList<>();
        for (final ServiceOrderDto item : data) {

            final ServiceOrder entity = getDomainEntityRepository().findOneByUid(item.getUid());

            if (entity.getStatus() == OrderStatus.IN_PROGRESS) {
                entity.setStatus(OrderStatus.AWAITING_APPRAISE);
            }

            final SimpleDateFormat df = new SimpleDateFormat("MMdd");
            final String txMonthAndDay = df.format(item.getTxDate());
            final PosTxDto tx = posProcessController.getTransaction(txMonthAndDay, item.getTxReferenceCode());
            final double amount = tx.getAmount();
            final String txCode = txMonthAndDay + "-" + item.getTxReferenceCode();

            AccountTransaction transaction;
            if (entity.getPaymentTransaction() == null) {
                transaction = new AccountTransaction();
            } else {
                transaction = entity.getPaymentTransaction();
                entity.setPriceChanged(FlagStatus.YES);
            }
            transaction.setCode(txCode);
            transaction.setStatus(RecordStatus.ACTIVE);
            transaction.setTimestamp(new Date());
            transaction.setType(TransactionType.ORDER_PAYMENT);
            accountTransactionRepository.save(transaction);
            entity.setPaymentTransaction(transaction);
            // entity.setExpectedPaymentAmount(amount);
            entity.setActualPaymentAmount(amount);

            final ServiceOrderOperation serviceOrderOperation = new ServiceOrderOperation();
            serviceOrderOperation.setOperation(OrderOperation.PAYED);
            serviceOrderOperation.setParams(txCode);
            try {
                serviceOrderOperation.setOperator(getSecurityUtil().getCurrentToken().getUsername());
            } catch (final IException e) {
            }
            serviceOrderOperation.setOrderStatus(OrderStatus.AWAITING_APPRAISE);
            serviceOrderOperation.setServiceOrder(entity);
            serviceOrderOperation.setStatus(RecordStatus.ACTIVE);
            serviceOrderOperation.setTimestamp(new Date());

            serviceOrderOperationRepository.save(serviceOrderOperation);

            entities.add(entity);
        }

        final RelationshipExpression rsexp = new RelationshipExpression("root");
        rsexp.getChildren().add(new RelationshipExpression("paymentTransaction"));
        return getDomainObjectConverter().convert(getDomainEntityRepository().save(entities), rsexp);
    }

    @Override
    @Transactional(rollbackOn = IException.class)
    public String uploadReceipt(final ServiceOrderDto serviceOrderDto) throws IException {
        final ServiceOrder order = getDomainEntityRepository().findOneByUid(serviceOrderDto.getUid());

        if (order == null) {
            throw getExceptionFactory().createException(GeneralErrorMessage.UNABLE_TO_FIND_INSTANCE);
        }

        Content content;
        if (StringUtils.isEmpty(order.getReceipt())) {
            content = new Content();
            content.setUuid(UUID.randomUUID().toString());
            content.setStatus(RecordStatus.ACTIVE);
            order.setReceipt(content.getUuid());
        } else {
            content = contentRepository.findOneByUuid(order.getReceipt());
        }

        if (order.getServiceInfo().getPaymentMethod() == PaymentMethod.POS) {
            order.setStatus(OrderStatus.AWAITING_APPRAISE);
        }

        content.setContent(serviceOrderDto.getReceiptContent());

        final ServiceOrderOperation serviceOrderOperation = new ServiceOrderOperation();
        serviceOrderOperation.setOperation(OrderOperation.RECEIPT_UPLOADED);
        serviceOrderOperation.setOperator(getSecurityUtil().getCurrentToken().getUsername());
        serviceOrderOperation.setOrderStatus(OrderStatus.AWAITING_APPRAISE);
        serviceOrderOperation.setServiceOrder(order);
        serviceOrderOperation.setStatus(RecordStatus.ACTIVE);
        serviceOrderOperation.setTimestamp(new Date());

        contentRepository.save(content);
        getDomainEntityRepository().save(order);
        serviceOrderOperationRepository.save(serviceOrderOperation);

        return order.getReceipt();
    }

    @Override
    protected void addRelatedTables(final ServiceOrder entity, final ServiceOrderDto dto) throws IException {
        super.addRelatedTables(entity, dto);
    }

    @Override
    protected void addRelationshipFields(final ServiceOrder entity, final ServiceOrderDto dto) throws IException {
        super.addRelationshipFields(entity, dto);
    }

    @Override
    protected boolean canAccessAllStatus() {
        return true;
    }

    @Override
    protected void updateRelatedTables(final ServiceOrder entity, final ServiceOrderDto dto) throws IException {
        super.updateRelatedTables(entity, dto);

        if (!dto.getOperations().isEmpty()) {
            serviceOrderOperationRepository.save(dto.getOperations().stream().map(item -> {
                final ServiceOrderOperation serviceOrderOperation = serviceOrderOperationConverter.convertBack(item);
                serviceOrderOperation.setServiceOrder(entity);
                serviceOrderOperation.setStatus(RecordStatus.ACTIVE);
                serviceOrderOperation.setTimestamp(new Date());
                serviceOrderOperation.setOrderStatus(entity.getStatus());
                try {
                    serviceOrderOperation.setOperator(getSecurityUtil().getCurrentToken().getUsername());
                } catch (final IException e) {
                }

                return serviceOrderOperation;
            }).collect(Collectors.toList()));

        }
    }

    @Override
    protected void updateRelationshipFields(final ServiceOrder entity, final ServiceOrderDto dto) throws IException {
        if (dto.getStaff() != null && dto.getStaff().getId() > 0) {
            entity.setServiceSupplierStaff(serviceSupplierStaffRepository.findOne(dto.getStaff().getId()));
        }
    }
}
