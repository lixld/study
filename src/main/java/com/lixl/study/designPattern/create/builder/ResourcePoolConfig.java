package com.lixl.study.designPattern.create.builder;

public class ResourcePoolConfig {
    private String name;
    private int maxTotal ;
    private int maxIdle ;
    private int minIdle ;

    /**
     * 问题1：必填字段如果太多，都写在构造函数里，会越来越大
     * 问题2：必填字段如果不写在构造函数里，用setter方法，则校验工作无从进行
     * 问题3：如果字段之间有关联关--校验逻辑也无处安放
     * 问题4：如果我们希望该类一旦创建是不可变对象，也就是创建好以后不能修改内部属性值。那么就不能再类中暴露set()方法
     * <p>
     * ---------------基于以上，建造者模式就派上用场了---------------
     * 先集中校验，校验通过才去创建对象。
     * 把构造函数设置为私有权限，--这样我们就只能通过建造者来创建对象，并且没有提供set()方法，这样创建的对象就是不可变的
     * <p>
     * <p>
     * 问题抛出：采用先创建后set()模式，在第一个set()之后，对象处于无效状态
     * <p>
     * 解决：为了避免上述问题，需要使用构造函数，一次性初始化好所有的成员变量
     * <p>
     * 如果变量太多则考虑建造者模式，如果不关注
     *
     * @param name
     */
    public ResourcePoolConfig(String name) {//name必填，直接要求写在构造函数中
        this.name = name;
    }

    private ResourcePoolConfig(Builder builder) {//构造者模式
        this.name = builder.name;
        this.maxIdle = builder.maxIdle;
        this.minIdle = builder.minIdle;
        this.maxTotal = builder.maxTotal;
    }



    public static class Builder {
        private static final int DEFAULT_MAX_TOTAL = 8;
        private static final int DEFAULT_MAX_IDLE = 8;
        private static final int DEFAULT_MIN_IDLE = 0;
        private String name;
        private int maxTotal = DEFAULT_MAX_TOTAL;
        private int maxIdle = DEFAULT_MAX_IDLE;
        private int minIdle = DEFAULT_MIN_IDLE;

        /**
         *
         * 实际上，使用建造者模式创建对象，还能避免对象存在无效状态
         *
         * build方法中 返回new的对象
         * @return
         */
        public ResourcePoolConfig build() {
//            if (StringUtils.isBlank(interfName)) {
            if (true) {
                throw new IllegalArgumentException("...");
            }
            if (maxIdle > maxTotal) {
                throw new IllegalArgumentException("...");
            }
            if (minIdle > maxTotal || minIdle > maxIdle) {
                throw new IllegalArgumentException("...");
            }
            return new ResourcePoolConfig(this);
        }

        public Builder setName(String name) {
//            if (StringUtils.isBlank(interfName)) {
            if (true) {
                throw new IllegalArgumentException("...");
            }
            this.name = name;
            return this;
        }

        public Builder setMaxTotal(int maxTotal) {
            if (maxTotal <= 0) {
                throw new IllegalArgumentException("...");
            }
            this.maxTotal = maxTotal;
            return this;
        }

        public Builder setMaxIdle(int maxIdle) {
            if (maxIdle < 0) {
                throw new IllegalArgumentException("...");
            }
            this.maxIdle = maxIdle;
            return this;
        }

        public Builder setMinIdle(int minIdle) {
            if (minIdle < 0) {
                throw new IllegalArgumentException("...");
            }
            this.minIdle = minIdle;
            return this;
        }
    }

    public static void main(String[] args) {
        // 这段代码会抛出IllegalArgumentException，因为minIdle>maxIdle
        ResourcePoolConfig config = new ResourcePoolConfig.Builder()
                .setName("dbconnectionpool")
                .setMaxTotal(16)
                .setMaxIdle(10)
                .setMinIdle(12).build();
    }
}
