package ${targetPackage};

/**
 * Messages used by the {@link ${processorClassName}}.
 */
public enum ${processorClassName}Messages {

    ;

    private static boolean printMessageCodes = false;
    private final String code;
    private final String message;

    private ${processorClassName}Messages(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return (printMessageCodes ? "[" + this.code + "] : " : "") + this.message;
    }

    public static void setPrintMessageCodes(final boolean printMessageCodes) {
        ${processorClassName}Messages.printMessageCodes = printMessageCodes;
    }

}
