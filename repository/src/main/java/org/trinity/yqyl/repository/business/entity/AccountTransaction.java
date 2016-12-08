//Cleaned
package org.trinity.yqyl.repository.business.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.trinity.repository.entity.AbstractAuditableEntity;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.common.message.lookup.TransactionType;

/**
 * The persistent class for the account_transaction database table.
 *
 */
@Entity
@Table(name = "account_transaction")
@NamedQuery(name = "AccountTransaction.findAll", query = "SELECT a FROM AccountTransaction a")
public class AccountTransaction extends AbstractAuditableEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "AccountTransaction_PK_IdGenerator")
    @TableGenerator(name = "AccountTransaction_PK_IdGenerator", table = "id_table", pkColumnName = "type", pkColumnValue = "AccountTransaction_PK", valueColumnName = "value", initialValue = 1, allocationSize = 1)
    private Long id;

    private String code;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    private RecordStatus status;

    private TransactionType type;

    // bi-directional many-to-one association to AccountPosting
    @OneToMany(mappedBy = "accountTransaction")
    private List<AccountPosting> accountPostings;

    @OneToOne(mappedBy = "paymentTransaction")
    private ServiceOrder paymentOrder;

    @OneToOne(mappedBy = "drawbackTransaction")
    private ServiceOrder drawbackOrder;

    public AccountTransaction() {
    }

    public AccountPosting addAccountPosting(final AccountPosting accountPosting) {
        getAccountPostings().add(accountPosting);
        accountPosting.setAccountTransaction(this);

        return accountPosting;
    }

    public List<AccountPosting> getAccountPostings() {
        return this.accountPostings;
    }

    public String getCode() {
        return this.code;
    }

    public ServiceOrder getDrawbackOrder() {
        return drawbackOrder;
    }

    public Long getId() {
        return this.id;
    }

    public ServiceOrder getPaymentOrder() {
        return paymentOrder;
    }

    public RecordStatus getStatus() {
        return this.status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public TransactionType getType() {
        return this.type;
    }

    public AccountPosting removeAccountPosting(final AccountPosting accountPosting) {
        getAccountPostings().remove(accountPosting);
        accountPosting.setAccountTransaction(null);

        return accountPosting;
    }

    public void setAccountPostings(final List<AccountPosting> accountPostings) {
        this.accountPostings = accountPostings;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setDrawbackOrder(final ServiceOrder drawbackOrder) {
        this.drawbackOrder = drawbackOrder;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setPaymentOrder(final ServiceOrder paymentOrder) {
        this.paymentOrder = paymentOrder;
    }

    public void setStatus(final RecordStatus status) {
        this.status = status;
    }

    public void setTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setType(final TransactionType type) {
        this.type = type;
    }

}
