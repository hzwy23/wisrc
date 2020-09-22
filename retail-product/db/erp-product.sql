/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018/3/14 8:53:06                            */
/*==============================================================*/


drop table if exists erp_product_Specifications_info;

drop table if exists erp_product_classify_define;

drop table if exists erp_product_classify_level_attr;

drop table if exists erp_product_declare_info;

drop table if exists erp_product_declare_label;

drop table if exists erp_product_declare_label_attr;

drop table if exists erp_product_define;

drop table if exists erp_product_details_info;

drop table if exists erp_product_handle_attr;

drop table if exists erp_product_image_classify_attr;

drop table if exists erp_product_images;

drop table if exists erp_product_machine_info;

drop table if exists erp_product_modify_history;

drop table if exists erp_product_status_attr;

/*==============================================================*/
/* Table: erp_product_Specifications_info                       */
/*==============================================================*/
create table erp_product_Specifications_info
(
   sku_id               varchar(60) not null,
   weight               decimal(24),
   length               decimal(10),
   width                decimal(10),
   height               decimal(10),
   fba_weight           decimal(24),
   fba_length           decimal(10),
   fba_width            decimal(10),
   fba_height           decimal(10),
   fba_volume           decimal(24),
   fba_quantity         decimal(24),
   primary key (sku_id)
);

/*==============================================================*/
/* Table: erp_product_classify_define                           */
/*==============================================================*/
create table erp_product_classify_define
(
   classify_cd          varchar(60) not null,
   classify_name_ch     varchar(200),
   classify_name_en     varchar(200),
   classify_short_name  varchar(5),
   level_cd             int,
   create_time          timestamp,
   create_user          varchar(60),
   parent_id            varchar(60),
   primary key (classify_cd)
);

/*==============================================================*/
/* Table: erp_product_classify_level_attr                       */
/*==============================================================*/
create table erp_product_classify_level_attr
(
   level_cd             int not null,
   level_desc           varchar(20),
   primary key (level_cd)
);

/*==============================================================*/
/* Table: erp_product_declare_info                              */
/*==============================================================*/
create table erp_product_declare_info
(
   sku_id               varchar(60) not null,
   Customs_number       varchar(200),
   Tax_rebate_point     decimal(10),
   ticket_name          varchar(200),
   issuing_office       varchar(200),
   declare_name_zh      varchar(200),
   declare_name_en      varchar(200),
   declare_price        decimal(24),
   single_weight        decimal(24),
   Declaration_elements varchar(2000),
   primary key (sku_id)
);

/*==============================================================*/
/* Table: erp_product_declare_label                             */
/*==============================================================*/
create table erp_product_declare_label
(
   sku_id               varchar(60),
   label_cd             int,
   uuid                 varchar(60) not null,
   primary key (uuid)
);

/*==============================================================*/
/* Table: erp_product_declare_label_attr                        */
/*==============================================================*/
create table erp_product_declare_label_attr
(
   label_cd             int not null,
   label_desc           varchar(100),
   primary key (label_cd)
);

/*==============================================================*/
/* Table: erp_product_define                                    */
/*==============================================================*/
create table erp_product_define
(
   sku_id               varchar(60) not null,
   sku_name_zh          varchar(60),
   status_cd            int,
   classify_cd          varchar(60),
   create_time          timestamp,
   create_user          varchar(60),
   machine_flag         int,
   sku_name_en          varchar(200),
   sku_short_name       varchar(5),
   primary key (sku_id)
);

alter table erp_product_define comment '定义产品信息';

/*==============================================================*/
/* Table: erp_product_details_info                              */
/*==============================================================*/
create table erp_product_details_info
(
   sku_id               varchar(60) not null,
   description          varchar(3000),
   primary key (sku_id)
);

/*==============================================================*/
/* Table: erp_product_handle_attr                               */
/*==============================================================*/
create table erp_product_handle_attr
(
   handle_type_cd       int not null,
   handle_type_desc     varchar(20),
   primary key (handle_type_cd)
);

/*==============================================================*/
/* Table: erp_product_image_classify_attr                       */
/*==============================================================*/
create table erp_product_image_classify_attr
(
   image_classify_cd    int not null,
   image_classify_desc  varchar(200),
   primary key (image_classify_cd)
);

/*==============================================================*/
/* Table: erp_product_images                                    */
/*==============================================================*/
create table erp_product_images
(
   uuid                 varchar(60) not null,
   sku_id               varchar(60),
   image_url            varchar(300),
   image_classify_cd    int,
   primary key (uuid)
);

/*==============================================================*/
/* Table: erp_product_machine_info                              */
/*==============================================================*/
create table erp_product_machine_info
(
   uuid                 varchar(60),
   sku_id               varchar(60),
   dependency_sku_id    varchar(60),
   quantity             int
);

alter table erp_product_machine_info comment '如果某一个产品需要其他产品组合而成，则需要录入依赖的产品信息';

/*==============================================================*/
/* Table: erp_product_modify_history                            */
/*==============================================================*/
create table erp_product_modify_history
(
   sku_id               varchar(60),
   modify_user          varchar(60),
   modify_time          timestamp,
   modify_column        varchar(60),
   old_value            varchar(200),
   new_value            varchar(200),
   handle_type_cd       int,
   uuid                 varchar(60) not null,
   primary key (uuid)
);

alter table erp_product_modify_history comment '用来记录产品的变更下信息';

/*==============================================================*/
/* Table: erp_product_status_attr                               */
/*==============================================================*/
create table erp_product_status_attr
(
   status_cd            int not null,
   status_desc          varchar(10),
   primary key (status_cd)
);

alter table erp_product_Specifications_info add constraint FK_Reference_10 foreign key (sku_id)
      references erp_product_define (sku_id) on delete restrict on update restrict;

alter table erp_product_classify_define add constraint FK_Reference_3 foreign key (level_cd)
      references erp_product_classify_level_attr (level_cd) on delete restrict on update restrict;

alter table erp_product_declare_info add constraint FK_Reference_5 foreign key (sku_id)
      references erp_product_define (sku_id) on delete restrict on update restrict;

alter table erp_product_declare_label add constraint FK_Reference_6 foreign key (label_cd)
      references erp_product_declare_label_attr (label_cd) on delete restrict on update restrict;

alter table erp_product_declare_label add constraint FK_Reference_7 foreign key (sku_id)
      references erp_product_declare_info (sku_id) on delete restrict on update restrict;

alter table erp_product_define add constraint FK_Reference_1 foreign key (status_cd)
      references erp_product_status_attr (status_cd) on delete restrict on update restrict;

alter table erp_product_define add constraint FK_Reference_2 foreign key (classify_cd)
      references erp_product_classify_define (classify_cd) on delete restrict on update restrict;

alter table erp_product_details_info add constraint FK_Reference_11 foreign key (sku_id)
      references erp_product_define (sku_id) on delete restrict on update restrict;

alter table erp_product_images add constraint FK_Reference_12 foreign key (sku_id)
      references erp_product_define (sku_id) on delete restrict on update restrict;

alter table erp_product_images add constraint FK_Reference_13 foreign key (image_classify_cd)
      references erp_product_image_classify_attr (image_classify_cd) on delete restrict on update restrict;

alter table erp_product_machine_info add constraint FK_Reference_4 foreign key (sku_id)
      references erp_product_define (sku_id) on delete restrict on update restrict;

alter table erp_product_modify_history add constraint FK_Reference_8 foreign key (sku_id)
      references erp_product_define (sku_id) on delete restrict on update restrict;

alter table erp_product_modify_history add constraint FK_Reference_9 foreign key (handle_type_cd)
      references erp_product_handle_attr (handle_type_cd) on delete restrict on update restrict;

