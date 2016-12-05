package org.trinity.yqyl.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.trinity.common.util.Tuple2;
import org.trinity.generator.EntityCleanupUtil;

public class EntityCleanUp {
    private static Map<String, List<Tuple2<String, String>>> userTypeMapping = new HashMap<>();
    static {

        List<Tuple2<String, String>> userTypes = new ArrayList<>();
        userTypes.add(new Tuple2<>("status", "AccountStatus"));
        userTypeMapping.put("Account", userTypes);

        userTypes = new ArrayList<>();
        userTypes.add(new Tuple2<>("status", "AccountBalanceStatus"));
        userTypes.add(new Tuple2<>("category", "AccountBalanceCategory"));
        userTypes.add(new Tuple2<>("currency", "Currency"));
        userTypeMapping.put("AccountBalance", userTypes);

        userTypes = new ArrayList<>();
        userTypes.add(new Tuple2<>("status", "AccountPostingStatus"));
        userTypes.add(new Tuple2<>("currency", "Currency"));
        userTypeMapping.put("AccountPosting", userTypes);

        userTypes = new ArrayList<>();
        userTypes.add(new Tuple2<>("status", "RecordStatus"));
        userTypes.add(new Tuple2<>("category", "TransactionCategory"));
        userTypeMapping.put("AccountTransaction", userTypes);

        userTypes = new ArrayList<>();
        userTypes.add(new Tuple2<>("status", "ServiceReceiverClientStatus"));
        userTypes.add(new Tuple2<>("type", "PersonalType"));
        userTypes.add(new Tuple2<>("gender", "Gender"));
        userTypeMapping.put("ServiceReceiverClient", userTypes);

        userTypes = new ArrayList<>();
        userTypes.add(new Tuple2<>("status", "ServiceSupplierClientStatus"));
        userTypes.add(new Tuple2<>("type", "PersonalType"));
        userTypeMapping.put("ServiceSupplierClient", userTypes);

        userTypes = new ArrayList<>();
        userTypes.add(new Tuple2<>("status", "ClientStatus"));
        userTypes.add(new Tuple2<>("type", "PersonalType"));
        userTypeMapping.put("AllowanceSupplierClient", userTypes);

        userTypes = new ArrayList<>();
        userTypes.add(new Tuple2<>("status", "ClientStatus"));
        userTypeMapping.put("OperatorClient", userTypes);

        userTypes = new ArrayList<>();
        userTypes.add(new Tuple2<>("status", "AnnouncementStatus"));
        userTypes.add(new Tuple2<>("clientType", "ClientType"));
        userTypeMapping.put("Announcement", userTypes);

        userTypes = new ArrayList<>();
        userTypes.add(new Tuple2<>("category", "LookupType"));
        userTypes.add(new Tuple2<>("language", "Language"));
        userTypes.add(new Tuple2<>("status", "RecordStatus"));
        userTypeMapping.put("Lookup", userTypes);

        userTypes = new ArrayList<>();
        userTypes.add(new Tuple2<>("status", "MessageStatus"));
        userTypes.add(new Tuple2<>("type", "MessageType"));
        userTypeMapping.put("Message", userTypes);

        userTypes = new ArrayList<>();
        userTypes.add(new Tuple2<>("status", "OrderStatus"));
        userTypeMapping.put("ServiceOrder", userTypes);

        userTypes = new ArrayList<>();
        userTypes.add(new Tuple2<>("status", "RecordStatus"));
        userTypes.add(new Tuple2<>("name", "RoleName"));
        userTypeMapping.put("Role", userTypes);

        userTypes = new ArrayList<>();
        userTypes.add(new Tuple2<>("status", "ServiceStatus"));
        userTypeMapping.put("Service", userTypes);

        userTypes = new ArrayList<>();
        userTypes.add(new Tuple2<>("status", "RecordStatus"));
        userTypeMapping.put("UserGroup", userTypes);

        userTypes = new ArrayList<>();
        userTypes.add(new Tuple2<>("status", "UserStatus"));
        userTypeMapping.put("User", userTypes);

        userTypes = new ArrayList<>();
        userTypes.add(new Tuple2<>("name", "AccessRight"));
        userTypes.add(new Tuple2<>("status", "RecordStatus"));
        userTypeMapping.put("Accessright", userTypes);

        userTypes = new ArrayList<>();
        userTypes.add(new Tuple2<>("status", "TokenStatus"));
        userTypeMapping.put("Token", userTypes);

        userTypes = new ArrayList<>();
        userTypes.add(new Tuple2<>("category", "FavoriteCategory"));
        userTypes.add(new Tuple2<>("status", "RecordStatus"));
        userTypeMapping.put("Favorite", userTypes);

        userTypes = new ArrayList<>();
        userTypes.add(new Tuple2<>("status", "RecordStatus"));
        userTypeMapping.put("ServiceCategory", userTypes);

        userTypes = new ArrayList<>();
        userTypes.add(new Tuple2<>("status", "RecordStatus"));
        userTypeMapping.put("Yiquan", userTypes);

        userTypes = new ArrayList<>();
        userTypes.add(new Tuple2<>("status", "RecordStatus"));
        userTypeMapping.put("ServiceSubOrder", userTypes);

        userTypes = new ArrayList<>();
        userTypes.add(new Tuple2<>("key", "SystemAttributeKey"));
        userTypes.add(new Tuple2<>("type", "ValueType"));
        userTypes.add(new Tuple2<>("status", "RecordStatus"));
        userTypeMapping.put("SystemAttribute", userTypes);

        userTypes = new ArrayList<>();
        userTypes.add(new Tuple2<>("status", "ServiceOrderRequestStatus"));
        userTypes.add(new Tuple2<>("announceStatus", "FlagStatus"));
        userTypeMapping.put("ServiceOrderRequest", userTypes);

    }

    public static void main(final String[] args) throws IOException {
        final File folder = new File("./src/main/java/org/trinity/yqyl/repository");
        EntityCleanupUtil.cleanUp(folder, "org.trinity.yqyl.repository", userTypeMapping);
    }
}
