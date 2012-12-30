/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2012/12/30 20:25:47                          */
/*==============================================================*/


drop table if exists biz_board;

drop table if exists biz_category;

drop table if exists biz_file;

drop table if exists biz_pin;

drop table if exists biz_pin_comment_user;

drop table if exists biz_pins_like_user;

drop table if exists biz_user;

drop table if exists biz_user_follow_board;

drop table if exists biz_user_follow_user;

drop table if exists sys_dictionary;

/*==============================================================*/
/* Table: biz_board                                             */
/*==============================================================*/
create table biz_board
(
   id                   bigint not null auto_increment comment '主键',
   title                varchar(100) comment '用户自定义画板标题',
   status               int comment '状态（1：存在；0：删除）',
   categoryId           bigint comment '画板分类',
   userId               bigint comment 'user 表外键',
   createTime           datetime comment '画板采集时间',
   updateTime           timestamp comment '画板修改时间',
   primary key (id)
);

alter table biz_board comment '画板';

/*==============================================================*/
/* Table: biz_category                                          */
/*==============================================================*/
create table biz_category
(
   id                   bigint not null auto_increment comment '主键',
   title                varchar(100) comment '用户自定义画板标题',
   status               int comment '状态（1：存在；0：删除）',
   createTime           datetime comment '画板采集时间',
   updateTime           timestamp comment '画板修改时间',
   comment              varchar(1000) comment '备注',
   primary key (id)
);

alter table biz_category comment '画板分类';

/*==============================================================*/
/* Table: biz_file                                              */
/*==============================================================*/
create table biz_file
(
   id                   bigint not null auto_increment comment '主键',
   width                int comment '图片宽度',
   height               bigint comment '图片高度',
   uuid                 varchar(60) comment '图片名（唯一标识）',
   mime                 varchar(25) comment '文件类型（如，image/jpeg; audio/basic; video/mpeg）',
   bucket               varchar(10) comment '文件服务器域名（待定）',
   primary key (id)
);

alter table biz_file comment '采集文件对象';

/*==============================================================*/
/* Table: biz_pin                                               */
/*==============================================================*/
create table biz_pin
(
   id                   bigint not null comment '主键',
   title                varchar(100) comment '用户自定义画板标题',
   status               int comment '状态（0：存在；1：删除）',
   userId               bigint comment 'user 表外键',
   createTime           datetime comment '画板采集时间',
   updateTime           timestamp comment '画板修改时间',
   boardId              bigint comment '采集所属画板id',
   media_type           int comment '媒体类型（图片、视频等，字典表定义）',
   link                 varchar(500) comment '采集来源网站页面地址',
   origSource           varchar(500) comment '采集对象的来源地址',
   fileId               bigint comment '文件id',
   text                 varchar(250) comment '采集描述',
   viaPinId             bigint comment '转采Id，自主外键（指向pins表的主键）',
   viaUserId            bigint comment '从谁那里采的（user 表id，冗余信息，方便查询）',
   primary key (id)
);

alter table biz_pin comment '采集内容';

/*==============================================================*/
/* Table: biz_pin_comment_user                                  */
/*==============================================================*/
create table biz_pin_comment_user
(
   id                   bigint not null auto_increment comment '主键',
   pinId                bigint comment '采集id（pins外键）',
   content              varchar(250) comment '评论内容',
   authorId             bigint comment '评论人id（user 表外键）',
   replyToUserId        bigint comment '回复用户id',
   createTime           timestamp comment '评论时间',
   primary key (id)
);

alter table biz_pin_comment_user comment '采集——评论';

/*==============================================================*/
/* Table: biz_pins_like_user                                    */
/*==============================================================*/
create table biz_pins_like_user
(
   id                   bigint not null auto_increment comment '主键',
   pinId                bigint comment '采集id（pins外键）',
   authorId             bigint comment '被赞采集所属作者Id（user 表外键）',
   userId               datetime comment '赞扬人（user 表外键）',
   createTime           timestamp comment '赞扬时间',
   primary key (id)
);

alter table biz_pins_like_user comment '采集——赞';

/*==============================================================*/
/* Table: biz_user                                              */
/*==============================================================*/
create table biz_user
(
   id                   bigint not null auto_increment comment '主键',
   username             varchar(25) comment '用户名（登录名），以邮箱登录？',
   oAuthName            char(10) comment '采用开放授权登录方式登录的登录名（允许多重身份登录）',
   alias                varchar(25) comment '别名',
   password             varchar(32) comment '登录密码',
   cityId               bigint comment '所在城市（int？varchar？）',
   about                varchar(250) comment '关于自己',
   avatarId             bigint comment '头像文件的id',
   urlName              varchar(25) comment '个性网址context',
   primary key (id)
);

alter table biz_user comment '注册用户表';

/*==============================================================*/
/* Table: biz_user_follow_board                                 */
/*==============================================================*/
create table biz_user_follow_board
(
   id                   bigint not null auto_increment comment '主键',
   biz_id               bigint comment '主键',
   followerId           bigint comment '关注者用户id',
   boardId              bigint comment '被关注画板id',
   createTime           timestamp comment '关注时间',
   primary key (id)
);

alter table biz_user_follow_board comment '关注 -- 画板';

/*==============================================================*/
/* Table: biz_user_follow_user                                  */
/*==============================================================*/
create table biz_user_follow_user
(
   id                   bigint not null auto_increment comment '主键',
   followerId           bigint comment '关注者用户id',
   beFollowerId         bigint comment '被关注者用户id',
   createTime           timestamp comment '关注时间',
   primary key (id)
);

alter table biz_user_follow_user comment '关注 -- 谁';

/*==============================================================*/
/* Table: sys_dictionary                                        */
/*==============================================================*/
create table sys_dictionary
(
   id                   bigint not null auto_increment comment '主键',
   groupName            varchar(30) comment '字典组查询名',
   groupNameDisp        char(10) comment '字典组显示名',
   dictKey              varchar(50) comment '典字key',
   dictValue            varchar(100) comment '典字value',
   dictDisp             char(10) comment '字典显示值',
   rank                 int comment '显示优先级',
   status               tinyint comment '否是启用',
   content              text comment '内容',
   pid                  bigint comment '上一级ID',
   primary key (id)
);

alter table biz_board add constraint FK_board_category foreign key (categoryId)
      references biz_category (id);

alter table biz_board add constraint FK_board_user foreign key (userId)
      references biz_user (id);

alter table biz_pin add constraint FK_file_pin foreign key (fileId)
      references biz_file (id);

alter table biz_pin add constraint FK_pin_board foreign key (boardId)
      references biz_board (id);

alter table biz_pin_comment_user add constraint FK_comment_pin foreign key (pinId)
      references biz_pin (id);

alter table biz_pin_comment_user add constraint FK_comment_user foreign key (authorId)
      references biz_user (id) on delete restrict on update restrict;

alter table biz_pins_like_user add constraint FK_like_pin foreign key (pinId)
      references biz_pin (id);

alter table biz_pins_like_user add constraint FK_like_user foreign key (userId)
      references biz_user (id);

alter table biz_user_follow_board add constraint FK_follow_board_user foreign key (biz_id)
      references biz_user (id);

alter table biz_user_follow_board add constraint FK_followe_board foreign key (id)
      references biz_board (id);

alter table biz_user_follow_user add constraint FK_beFollow_user foreign key (beFollowerId)
      references biz_user (id);

alter table biz_user_follow_user add constraint FK_follow_user foreign key (followerId)
      references biz_user (id);

