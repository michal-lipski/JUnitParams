package junitparams.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;

import junitparams.Named;

/**
 * Some String utils to handle parameterised tests' results.
 *
 * @author Pawel Lipinski
 */
public class Utils {
    public static final String REGEX_ALL_NEWLINES = "(\\r\\n|\\n|\\r)";

    public static String testCaseResultRepresentation(Object paramSet, int paramIdx, Annotation[][] parameterAnnotations) {
        String result = "[" + paramIdx + "] ";

        if (paramSet == null) {
            result += "null";
        } else if (paramSet instanceof String) {
            result += appendParamsValues(paramSet, parameterAnnotations);
        } else {
            result += asCsvString(safelyCastParamsToArray(paramSet), paramIdx);
        }

        return trimSpecialChars(result);
    }

    private static String appendParamsValues(Object paramSet, Annotation[][] parameterAnnotations) {

        String[] paramsValues = ((String) paramSet).split(",");

        StringBuilder testCaseName = new StringBuilder();

        for (int i = 0; i < paramsValues.length; i++) {
            StringBuilder paramDisplay = new StringBuilder();
            if (parameterAnnotations.length >= i + 1) {
                for (Annotation annotation : parameterAnnotations[i]) {
                    if (annotation.annotationType().equals(Named.class) && ((Named) annotation).value() != null) {
                        String paramNameFromAnnotation = ((Named) annotation).value();
                        paramDisplay.append(paramNameFromAnnotation).append(":");
                    }
                }
            }

            paramDisplay.append(paramsValues[i]);
            if (i != paramsValues.length-1) {
                paramDisplay.append(",");
            }
            testCaseName.append(paramDisplay);
        }

        return testCaseName.toString();
    }

    private static String trimSpecialChars(String result) {
        return result.replace('(', '[').replace(')', ']').replaceAll(REGEX_ALL_NEWLINES, " ");
    }

    static Object[] safelyCastParamsToArray(Object paramSet) {
        Object[] params;

        if (paramSet instanceof String[]) {
            params = new Object[]{paramSet};
        } else {
            try {
                params = (Object[]) paramSet;
            } catch (ClassCastException e) {
                params = new Object[]{paramSet};
            }
        }
        return params;
    }

    private static String asCsvString(Object[] params, int paramIdx) {
        if (params == null)
            return "null";

        if (params.length == 0)
            return "";

        String result = "";

        for (int i = 0; i < params.length - 1; i++) {
            Object param = params[i];
            result = addParamToResult(result, param) + ", ";
        }
        result = addParamToResult(result, params[params.length - 1]);

        return result;
    }

    private static String addParamToResult(String result, Object param) {
        if (param == null)
            result += "null";
        else {
            try {
                tryFindingOverridenToString(param);
                result += param.toString();
            } catch (Exception e) {
                result += param.getClass().getSimpleName();
            }
        }
        return result;
    }

    private static void tryFindingOverridenToString(Object param)
            throws NoSuchMethodException {
        final Method toString = param.getClass().getMethod("toString");

        if (toString.getDeclaringClass().equals(Object.class)) {
            throw new NoSuchMethodException();
        }
    }
}
