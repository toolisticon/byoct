package io.toolisticon.byoct.processor;

/**
 * Messages used by the {@link ByoctProcessor}.
 */
public enum ByoctProcessorMessages {

    ERROR_COULD_NOT_GENERATE_ANNOTATION_PROCESSOR("ERROR_001", "Could not create annotation processor: ${0}"),
    ERROR_COULD_NOT_GENERATE_POM_XML("ERROR_002", "Could not create pom.xml"),
    ERROR_COULD_NOT_GENERATE_UNIT_TEST("ERROR_003", "Could not create unit test for annotation processor: ${0}"),
    ;

    private static boolean printMessageCodes;
    private final String code;
    private final String message;

    private ByoctProcessorMessages(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return (printMessageCodes ? "[" + this.code + "] : " : "") + this.message;
    }

    public static void setPrintMessageCodes(boolean printMessageCodes) {
        printMessageCodes = printMessageCodes;
    }

    static {
        printMessageCodes = false;
    }

}
