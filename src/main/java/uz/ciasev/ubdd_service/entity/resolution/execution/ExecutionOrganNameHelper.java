package uz.ciasev.ubdd_service.entity.resolution.execution;

public class ExecutionOrganNameHelper {

    static public String append(String executionOrganName, String newName) {
        if (executionOrganName == null) {
            return newName;
        } else {
            if (executionOrganName.contains(newName)) {
                return executionOrganName;
            }

            return cleanExecutionOrganName(executionOrganName + "," + newName);
        }

    }

    static public String remove(String executionOrganName, String newName) {
        if (executionOrganName == null) {
            return null;
        }

        return cleanExecutionOrganName(executionOrganName.replaceAll(newName, ""));
    }

    static private String cleanExecutionOrganName(String executionOrganName) {
        if (executionOrganName == null || executionOrganName.isBlank()) {
            return null;
        }

        String cleaned = executionOrganName.replaceAll(",,", ",");

        if (cleaned.endsWith(",")) {
            cleaned = executionOrganName.substring(0, cleaned.length() - 1);
        }

        if (cleaned.startsWith(",")) {
            cleaned = cleaned.substring(1);
        }

        return cleaned;
    }
}
