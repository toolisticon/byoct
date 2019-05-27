package ${targetPackage};

import io.toolisticon.annotationprocessortoolkit.tools.corematcher.ValidationMessage;

/**
 * Messages used by the {@link ${processorClassName}}.
 */
public enum ${processorClassName}Messages implements ValidationMessage {

    //ERROR_COULD_NOT_CREATE_BUILDER_CLASS("SERVICE_ERROR_001", "Could not create Builder class ${0} : ${1}")
    ;


    /**
     * the message code.
     */
    private final String code;

    /**
     * the message text.
     */
    private final String message;

    /**
     * Constructor.
     *
     * @param code    the message code
     * @param message the message text
     */
    ${processorClassName}Messages(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Gets the code of the message.
     *
     * @return the message code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Gets the message text.
     *
     * @return the message text
     */
    public String getMessage() {
        return message;
    }


}

