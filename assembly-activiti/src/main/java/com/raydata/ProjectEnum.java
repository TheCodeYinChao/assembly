package com.raydata;

public interface ProjectEnum {

    enum ProjectStatusEnum {
        CREATE(10010, "立项"),
        PASS_APPROVAL(10011, "审批通过");

        private int code;
        private String name;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        ProjectStatusEnum(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public static ProjectStatusEnum getProjectStatusEnumByCode(int code) {
            ProjectStatusEnum[] projectStatusEnums = ProjectStatusEnum.values();
            for (ProjectStatusEnum projectStatusEnum : projectStatusEnums) {
                if (projectStatusEnum.getCode() == code) return projectStatusEnum;
            }
            return null;
        }

    }

}
