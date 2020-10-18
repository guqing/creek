create table MENU
(
    ID          BIGINT auto_increment
        primary key,
    PARENT_ID   BIGINT       not null,
    TITLE       VARCHAR(100) not null,
    NAME        VARCHAR(50),
    PATH        VARCHAR(255),
    REDIRECT    VARCHAR(150),
    COMPONENT   VARCHAR(255),
    ICON        VARCHAR(50),
    KEEP_ALIVE  INT,
    HIDDEN      INT    default 1,
    PERMS       VARCHAR(50),
    TYPE        CHAR(2)      not null,
    SORT_INDEX  BIGINT default 0,
    CREATE_TIME DATETIME     not null,
    MODIFY_TIME DATETIME
);

comment on column MENU.ID is '菜单/按钮ID';

comment on column MENU.PARENT_ID is '上级菜单ID';

comment on column MENU.TITLE is '菜单或按钮的标题';

comment on column MENU.NAME is '组件名称';

comment on column MENU.PATH is '对应路由path';

comment on column MENU.REDIRECT is '重定向到路径';

comment on column MENU.COMPONENT is '对应路由组件component';

comment on column MENU.ICON is '图标';

comment on column MENU.HIDDEN is '控制路由和子路由是否显示在 sidebar';

comment on column MENU.PERMS is '权限标识';

comment on column MENU.TYPE is '类型 0菜单 1按钮';

comment on column MENU.SORT_INDEX is '排序';

comment on column MENU.CREATE_TIME is '创建时间';

comment on column MENU.MODIFY_TIME is '修改时间';

create index MENU_ID
    on MENU (ID);

create index MENU_PARENT_ID
    on MENU (PARENT_ID);

create table ROLE
(
    ID          BIGINT auto_increment
        primary key,
    ROLE_NAME   VARCHAR(10)   not null,
    REMARK      VARCHAR(100),
    IS_DEFAULT  INT default 0 not null,
    DELETED     INT default 0,
    CREATE_TIME DATETIME      not null,
    MODIFY_TIME DATETIME
);

comment on column ROLE.ID is '角色ID';

comment on column ROLE.ROLE_NAME is '角色名称';

comment on column ROLE.REMARK is '角色描述';

comment on column ROLE.IS_DEFAULT is '是否是默认角色';

comment on column ROLE.DELETED is '删除状态';

comment on column ROLE.CREATE_TIME is '创建时间';

comment on column ROLE.MODIFY_TIME is '修改时间';


create table ROLE_MENU
(
    ROLE_ID BIGINT not null,
    MENU_ID BIGINT not null,
    primary key (ROLE_ID, MENU_ID)
);

create index ROLE_MENU_MENU_ID
    on ROLE_MENU (MENU_ID);

create index ROLE_MENU_ROLE_ID
    on ROLE_MENU (ROLE_ID);


create table USER
(
    ID              BIGINT auto_increment
        primary key,
    USERNAME        VARCHAR(50)  not null,
    PASSWORD        VARCHAR(128) not null,
    NICKNAME        VARCHAR(100) default '',
    GROUP_ID        BIGINT,
    EMAIL           VARCHAR(128)
        constraint USER_EMAIL_UINDEX
            unique,
    MOBILE          VARCHAR(20)
        constraint RMS_USER_MOBILE
            unique,
    GENDER          INT,
    IS_TAB          INT,
    THEME           VARCHAR(10),
    AVATAR          VARCHAR(100),
    DESCRIPTION     VARCHAR(150),
    LAST_LOGIN_TIME DATETIME,
    STATUS          INT          not null,
    DELETED         INT          default 0,
    CREATE_TIME     DATETIME     not null,
    MODIFY_TIME     DATETIME
);

comment on column USER.ID is '用户ID';

comment on column USER.USERNAME is '用户名';

comment on column USER.PASSWORD is '密码';

comment on column USER.NICKNAME is '昵称';

comment on column USER.GROUP_ID is '用户组';

comment on column USER.EMAIL is '邮箱';

comment on column USER.MOBILE is '联系电话';

comment on column USER.GENDER is '性别 0男 1女 2保密';

comment on column USER.IS_TAB is '是否开启tab，0关闭 1开启';

comment on column USER.THEME is '主题';

comment on column USER.AVATAR is '头像';

comment on column USER.DESCRIPTION is '描述';

comment on column USER.LAST_LOGIN_TIME is '最近访问时间';

comment on column USER.STATUS is '状态 0锁定 1有效';

comment on column USER.DELETED is '删除状态，0正常，1已删除';

comment on column USER.CREATE_TIME is '创建时间';

comment on column USER.MODIFY_TIME is '修改时间';

create index USER_USERNAME
    on USER (USERNAME);


create table USER_CONNECTION
(
    USER_ID          VARCHAR(50) not null,
    PROVIDER_NAME      VARCHAR(20) not null,
    PROVIDER_USER_ID   VARCHAR(50) not null,
    PROVIDER_USER_NAME VARCHAR(50),
    NICK_NAME          VARCHAR(50),
    AVATAR             VARCHAR(512),
    LOCATION           VARCHAR(255),
    REMARK             VARCHAR(255),
    primary key (USER_ID, PROVIDER_NAME, PROVIDER_USER_ID),
    constraint USER_CONNECTION_RANK
        unique (USER_ID, PROVIDER_NAME, PROVIDER_USER_ID)
);

comment on column USER_CONNECTION.USER_ID is '系统用户ID';

comment on column USER_CONNECTION.PROVIDER_NAME is '第三方平台名称';

comment on column USER_CONNECTION.PROVIDER_USER_ID is '第三方平台账户ID';

comment on column USER_CONNECTION.PROVIDER_USER_NAME is '第三方平台用户名';

comment on column USER_CONNECTION.NICK_NAME is '第三方平台昵称';

comment on column USER_CONNECTION.AVATAR is '第三方平台头像';

comment on column USER_CONNECTION.LOCATION is '地址';

comment on column USER_CONNECTION.REMARK is '备注';


create table USER_GROUP
(
    ID          BIGINT auto_increment
        primary key,
    PARENT_ID   BIGINT       not null,
    GROUP_NAME  VARCHAR(100) not null,
    SORT_INDEX  BIGINT,
    CREATE_TIME DATETIME,
    MODIFY_TIME DATETIME
);

comment on column USER_GROUP.ID is '用户组id';

comment on column USER_GROUP.PARENT_ID is '上级分组id';

comment on column USER_GROUP.GROUP_NAME is '分组名称';

comment on column USER_GROUP.SORT_INDEX is '排序';

comment on column USER_GROUP.CREATE_TIME is '创建时间';

comment on column USER_GROUP.MODIFY_TIME is '修改时间';

create index USER_GROUP_GROUP_NAME
    on USER_GROUP (GROUP_NAME);

create index USER_GROUP_PARENT_ID
    on USER_GROUP (PARENT_ID);


create table USER_LOGIN_LOG
(
    ID         BIGINT auto_increment
        primary key,
    USERNAME   VARCHAR(50) not null,
    LOGIN_TIME DATETIME    not null,
    LOCATION   VARCHAR(50),
    IP         VARCHAR(50),
    SYSTEM     VARCHAR(50),
    BROWSER    VARCHAR(50)
);

comment on column USER_LOGIN_LOG.ID is 'id';

comment on column USER_LOGIN_LOG.USERNAME is '用户名';

comment on column USER_LOGIN_LOG.LOGIN_TIME is '登录时间';

comment on column USER_LOGIN_LOG.LOCATION is '登录地点';

comment on column USER_LOGIN_LOG.IP is 'IP地址';

comment on column USER_LOGIN_LOG.SYSTEM is '操作系统';

comment on column USER_LOGIN_LOG.BROWSER is '浏览器';

create index USER_LOGIN_LOG_LOGIN_TIME
    on USER_LOGIN_LOG (LOGIN_TIME);


create table USER_ROLE
(
    USER_ID BIGINT not null,
    ROLE_ID BIGINT not null,
    primary key (USER_ID, ROLE_ID)
);

comment on column USER_ROLE.USER_ID is '用户ID';

comment on column USER_ROLE.ROLE_ID is '角色ID';


create table ACTION_LOG
(
    ID          BIGINT auto_increment
        primary key,
    USERNAME    VARCHAR(50),
    OPERATION   TEXT,
    EXECUTION_TIME  DECIMAL(11),
    METHOD      TEXT,
    PARAMS      TEXT,
    IP          VARCHAR(64),
    LOCATION    VARCHAR(50),
    CREATE_TIME DATETIME
);

comment on column ACTION_LOG.ID is '日志ID';

comment on column ACTION_LOG.USERNAME is '操作用户';

comment on column ACTION_LOG.OPERATION is '操作内容';

comment on column ACTION_LOG.EXECUTION_TIME is '耗时';

comment on column ACTION_LOG.METHOD is '操作方法';

comment on column ACTION_LOG.PARAMS is '方法参数';

comment on column ACTION_LOG.IP is '操作者IP';

comment on column ACTION_LOG.LOCATION is '操作地点';

comment on column ACTION_LOG.CREATE_TIME is '创建时间';

create index ACTION_LOG_CREATE_TIME
    on ACTION_LOG (CREATE_TIME);

create table SETTING_OPTION
(
    ID           BIGINT auto_increment
        primary key,
    OPTION_KEY   VARCHAR(100) not null
        constraint SETTING_OPTION_OPTION_KEY_UINDEX
            unique,
    OPTION_VALUE VARCHAR(100) not null,
    CREATE_TIME  DATETIME     not null,
    MODIFY_TIME  DATETIME     not null
);

comment on column SETTING_OPTION.OPTION_KEY is 'key名称';

comment on column SETTING_OPTION.OPTION_VALUE is '值';

comment on column SETTING_OPTION.CREATE_TIME is '创建时间';

comment on column SETTING_OPTION.MODIFY_TIME is '修改时间';