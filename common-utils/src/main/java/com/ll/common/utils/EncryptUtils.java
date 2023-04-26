package com.ll.common.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;

public final class EncryptUtils {

    private static final long DEFAULT_TIMESTAMP_EXPIRE_MINUTES = -1;
    private static final int DEFAULT_TIMESTAMP_LENGTH = 13;

    public static final String RSA_PRIVATE_KEY = "MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQDlLPFIz1KVQH9+ILN5zpdFXEtiPLgVuRS/11PwZWpIHrDfZX85uELwF+TwOD33/8ycz302cOtU8IuhZ0plhw0cvTlgGURzglAZtEvd7ASTVCuuBMLWG7aq9RF+goW9zOWYlUJjSMQZ1uGrQUwqraCyJtSJM4VaubaHOG4C8ugnz5NnqgtA/UyrQIGwac+wZTKVbLvDgR02h72YmUZzvZG5QA9nkanTBKlu2Kwa6pf5Pp94ah9pDGRO6HzwmKk28m2vdIjjVZuwRnVHYm4vn3GVJyOW22K8kXufWvkgTfMJlf4EQh4yzyemlDv2VraK5EXVyBIeUFyT0LsO3XULklrBAgMBAAECggEBAKlg9w++EyY2XkQk/SEYnFrqJNj9UXzUps5G4kX5XnzhGg5yIH7Tfb2fm1gj6UEC8QIwpDV5baUAh+KJycWR9BH6lI4+/oV0maj1xWDG0HbnLcVWxX2jS/W8XOqooxotdr+4CTICFNWYKH//KcEdRHiHFGw3BOeR/KKiBUTurMDGVq5wyVpZFbTZS1lii/YyN3CibBhJWoVp5Me8MVH6bQYPAnhrsRdvQk7Kl6DbUFZrQBIltJzKaLdK1L6mElYIEUd4yL0B4UGBhZCDtzDlefICV3A5Drd80CWVhr841c+HCHxhODceIQYJzxsy91BPEdKAYG+91peVqX5kxwZ7BHECgYEA/GvfV9vOKRL/iRTYHn0bQ/JVWJrPefsFsGOt1OuamkoflafxOnf6X/2UtWZLtJY3r8nJv9KqbFV/cEnLB7O5l5b7+6icp/ZO4b8bP3/7Y2eIqOdOAM0toGHB/kL4IebmIys+Lqm8AaBl0PRSfTBCG0J+mdI+i6piVvTEAe2zXjUCgYEA6Gyz4rUx2EPRMsXvmnWcre+uHirTpiVVl2FMv9Ie0VCGeN/MBPq7KycRHiGByWwGU2wxRi3EWwVSAXR3wxgkM4Du0AO0lG06GHyuSbTHouQbAHkETNwuSDg8HJXpu+3mJlr8VRgo3V9oP4FWQ4QcHITYw1SqzNcJjdFfl8Suy90CgYEAgqBkXTgvsQvW157E1MpTYM84oXjD49EmuYhr/eLLCAtR2wWlDWfnVuOIoHNGvDZ8rOjVEgK4FQYNxBM8EUlNwwfscOhp4XXSjnpyZGMbue5rR1UkBcAoW3YbiTQo6opuEEchj85gr8rMxdyaD5T4sNixYXLu0SIkb/GMuZ7EEv0CgYEAzwkVa9rPnb87OCkM99/QQzf/N6wagCM9jO0FOtFm1jkOr9lamQzncNcJ7ncWb1+keucjNUpLYHDqzZT7oXN9u8sb73t9fcGWQH2/mrRIKJdcXh1ucTCooXhGQ4hefiK6Imxl3T76F8eGyxrgxp+FkvnbRJPE2VarkgJxTnXK5pkCgYEA5MWuTAz8Q7bzL9jQuHUyVMmtvYlalaBMW9IT7WGVNAduFWUU4Jvw51dEzCFC2tG5B1G/bINj9CGvnQV8b68k8bG35WHSqx6oPhRsWGPmHMc7r9XpsMMBWCyChMUeY+zzrZfNSIVvsUVgKkUKNF9OfiV/PuBitoLuWvixXVumEWE=";

    public static String sha256WithRSA(String content, String privateKey) {
        Sign sign = SecureUtil.sign(SignAlgorithm.SHA256withRSA, privateKey, null);
        return Base64.encode(sign.sign(content));
    }

    public static String decodeRSAPrivateKey(String content, String privateKey) {
        if (StringUtils.isEmpty(content)) {
            //todo
            // throw new BusinessException("密文为空");
        }
        RSA rsa = SecureUtil.rsa(privateKey, null);
        return rsa.decryptStr(content, KeyType.PrivateKey);
    }

    public static String decodeRSAPrivateKey(String content) {
        if (StringUtils.isEmpty(content)) {
            //   throw new BusinessException("密文为空");
        }
        RSA rsa = SecureUtil.rsa(RSA_PRIVATE_KEY, null);
        return rsa.decryptStr(content, KeyType.PrivateKey);
    }

    public static String decodeRSAPrivateKey(String content, Long timestamp) {
        long currentTimestamp = System.currentTimeMillis();
        Long diffMillis = currentTimestamp > timestamp ? currentTimestamp - timestamp : timestamp - currentTimestamp;
        Long expireMillis = DEFAULT_TIMESTAMP_EXPIRE_MINUTES < 0 ? diffMillis : DEFAULT_TIMESTAMP_EXPIRE_MINUTES * 60 * 1000;
        if (diffMillis.compareTo(expireMillis) > 0) {
            //todo
            //   throw new BusinessException("时间戳已失效");
        }
        String decodedContent = decodeRSAPrivateKey(content);
        if (StringUtils.isEmpty(decodedContent) || StringUtils.length(decodedContent) < DEFAULT_TIMESTAMP_LENGTH) {
            //   throw new BusinessException("原文格式错误");
        }
        if (StringUtils.equals(String.valueOf(timestamp), decodedContent.substring(0, DEFAULT_TIMESTAMP_LENGTH - 1))) {
            //throw new BusinessException("时间戳不一致");
        }
        return decodedContent.substring(DEFAULT_TIMESTAMP_LENGTH);
    }



}
