package com.lixl.study.designPattern.create.builder;

public class ConstructorArg {
    private boolean isRef;
    private Class type;
    private Object arg; // TODO: 待完善...

    private ConstructorArg(Builder builder) {

    }

    public static class Builder {
        private boolean isRef;
        private Class type;
        private Object arg;

        public Builder setRef(boolean ref) {
            isRef = ref;
            return this;
        }

        public Builder setType(Class type) {
            this.type = type;
            return this;
        }

        public Builder setArg(Object arg) {
            this.arg = arg;
            return this;
        }

        public ConstructorArg build() {
            boolean checkPass = false;
            //校验
            if (isRef) {
                if (arg instanceof String) {
                    checkPass = true;
                }
            } else {
                if (type != null && arg != null) {
                    checkPass = true;
                }
            }
            if (checkPass) {
                return new ConstructorArg(this);
            } else {
                throw new IllegalArgumentException("-----");
            }

        }
    }

    public static void main(String[] args) {
        new ConstructorArg.Builder().setArg("sdfs").setRef(false).setType(null).build();
    }
}
