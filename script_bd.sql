
/*==============================================================*/
/* DBMS name:      PostgreSQL 8                                 */
/* Created on:     14/04/2019 15:31:20                          */
/*==============================================================*/


drop index CATEGORY_PK;

drop table CATEGORY;

drop index DISPOE2_FK;

drop index DISPOE_FK;

drop index DISPOE_PK;

drop table DISPOE;

drop index PERTENCE_FK;

drop index CRIA_FK;

drop index AGREGA_FK;

drop index GOAL_PK;

drop table GOAL;

drop index AGREGA2_FK;

drop index RECORDA_FK;

drop index HISTORY_PK;

drop table HISTORY;

drop index RANK_PK;

drop table RANK;

drop index STANDARD_GOAL_PK;

drop table STANDARD_GOAL;

drop index PREMIADO_FK;

drop index TROPHY_PK;

drop table TROPHY;

drop index CONVERSAR_FK;

drop index TEM_FK;

drop index USER_PK;

drop table "USER";

/*==============================================================*/
/* Table: CATEGORY                                              */
/*==============================================================*/
create table CATEGORY (
   ID_CATEGORY          SERIAL               not null,
   NAME                 VARCHAR(1024)        not null,
   "DESC"               VARCHAR(500)         not null,
   constraint PK_CATEGORY primary key (ID_CATEGORY)
);

/*==============================================================*/
/* Index: CATEGORY_PK                                           */
/*==============================================================*/
create unique index CATEGORY_PK on CATEGORY (
ID_CATEGORY
);

/*==============================================================*/
/* Table: DISPOE                                                */
/*==============================================================*/
create table DISPOE (
   ID_STANDARD          INT4                 not null,
   ID_USER              INT4                 not null,
   constraint PK_DISPOE primary key (ID_STANDARD, ID_USER)
);

/*==============================================================*/
/* Index: DISPOE_PK                                             */
/*==============================================================*/
create unique index DISPOE_PK on DISPOE (
ID_STANDARD,
ID_USER
);

/*==============================================================*/
/* Index: DISPOE_FK                                             */
/*==============================================================*/
create  index DISPOE_FK on DISPOE (
ID_STANDARD
);

/*==============================================================*/
/* Index: DISPOE2_FK                                            */
/*==============================================================*/
create  index DISPOE2_FK on DISPOE (
ID_USER
);

/*==============================================================*/
/* Table: GOAL                                                  */
/*==============================================================*/
create table GOAL (
   ID_GOAL              SERIAL               not null,
   ID_USER              INT4                 not null,
   ID_HISTORY           INT4                 null,
   ID_CATEGORY          INT4                 not null,
   NAME                 VARCHAR(1024)        not null,
   "DESC"               VARCHAR(500)         not null,
   TYPE                 VARCHAR(1024)        not null,
   STATUS               VARCHAR(1024)        not null,
   FINALDATE            DATE                 not null,
   TOTALVALUE           INT4                 not null,
   CURRENTVALUE         INT4                 not null,
   FAVORITE             BOOL                 not null,
   LOGDATE              DATE                 not null,
   FLAG_CLICK_CONTROL   INT4                 not null,
   FLAG_ORDER           INT4                 not null,
   constraint PK_GOAL primary key (ID_GOAL)
);

/*==============================================================*/
/* Index: GOAL_PK                                               */
/*==============================================================*/
create unique index GOAL_PK on GOAL (
ID_GOAL
);

/*==============================================================*/
/* Index: AGREGA_FK                                             */
/*==============================================================*/
create  index AGREGA_FK on GOAL (
ID_HISTORY
);

/*==============================================================*/
/* Index: CRIA_FK                                               */
/*==============================================================*/
create  index CRIA_FK on GOAL (
ID_USER
);

/*==============================================================*/
/* Index: PERTENCE_FK                                           */
/*==============================================================*/
create  index PERTENCE_FK on GOAL (
ID_CATEGORY
);

/*==============================================================*/
/* Table: HISTORY                                               */
/*==============================================================*/
create table HISTORY (
   ID_HISTORY           SERIAL               not null,
   ID_USER              INT4                 not null,
   ID_GOAL              INT4                 not null,
   TYPE                 VARCHAR(1024)        not null,
   constraint PK_HISTORY primary key (ID_HISTORY)
);

/*==============================================================*/
/* Index: HISTORY_PK                                            */
/*==============================================================*/
create unique index HISTORY_PK on HISTORY (
ID_HISTORY
);

/*==============================================================*/
/* Index: RECORDA_FK                                            */
/*==============================================================*/
create  index RECORDA_FK on HISTORY (
ID_USER
);

/*==============================================================*/
/* Index: AGREGA2_FK                                            */
/*==============================================================*/
create  index AGREGA2_FK on HISTORY (
ID_GOAL
);

/*==============================================================*/
/* Table: RANK                                                  */
/*==============================================================*/
create table RANK (
   ID_RANK              INT4                 not null,
   NAME                 VARCHAR(1024)        not null,
   "DESC"               VARCHAR(500)         not null,
   MINPOINTS            INT4                 not null,
   constraint PK_RANK primary key (ID_RANK)
);

/*==============================================================*/
/* Index: RANK_PK                                               */
/*==============================================================*/
create unique index RANK_PK on RANK (
ID_RANK
);

/*==============================================================*/
/* Table: STANDARD_GOAL                                         */
/*==============================================================*/
create table STANDARD_GOAL (
   ID_STANDARD          SERIAL               not null,
   NAME                 VARCHAR(1024)        not null,
   "DESC"               VARCHAR(500)         not null,
   TYPE                 VARCHAR(1024)        not null,
   STATUS               VARCHAR(1024)        not null,
   TOTALVALUE           INT4                 not null,
   FLAG_CLICK_CONTROL   INT4                 not null,
   FLAG_ORDER           INT4                 not null,
   constraint PK_STANDARD_GOAL primary key (ID_STANDARD)
);

/*==============================================================*/
/* Index: STANDARD_GOAL_PK                                      */
/*==============================================================*/
create unique index STANDARD_GOAL_PK on STANDARD_GOAL (
ID_STANDARD
);

/*==============================================================*/
/* Table: TROPHY                                                */
/*==============================================================*/
create table TROPHY (
   ID_TROPHY            SERIAL               not null,
   ID_USER              INT4                 not null,
   NAME                 VARCHAR(1024)        not null,
   "DESC"               VARCHAR(500)         not null,
   VALUE                INT4                 not null,
   constraint PK_TROPHY primary key (ID_TROPHY)
);

/*==============================================================*/
/* Index: TROPHY_PK                                             */
/*==============================================================*/
create unique index TROPHY_PK on TROPHY (
ID_TROPHY
);

/*==============================================================*/
/* Index: PREMIADO_FK                                           */
/*==============================================================*/
create  index PREMIADO_FK on TROPHY (
ID_USER
);

/*==============================================================*/
/* Table: "USER"                                                */
/*==============================================================*/
create table "USER" (
   ID_USER              SERIAL               not null,
   ID_RANK              INT4                 not null,
   USE_ID_USER          INT4                 null,
   EMAIL                VARCHAR(1024)        not null,
   PASSWORD             VARCHAR(500)         not null,
   NAME                 VARCHAR(1024)        not null,
   AGE                  DATE                 not null,
   GENRE                VARCHAR(500)         not null,
   constraint PK_USER primary key (ID_USER)
);

/*==============================================================*/
/* Index: USER_PK                                               */
/*==============================================================*/
create unique index USER_PK on "USER" (
ID_USER
);

/*==============================================================*/
/* Index: TEM_FK                                                */
/*==============================================================*/
create  index TEM_FK on "USER" (
ID_RANK
);

/*==============================================================*/
/* Index: CONVERSAR_FK                                          */
/*==============================================================*/
create  index CONVERSAR_FK on "USER" (
USE_ID_USER
);

alter table DISPOE
   add constraint FK_DISPOE_DISPOE_STANDARD foreign key (ID_STANDARD)
      references STANDARD_GOAL (ID_STANDARD)
      on delete restrict on update restrict;

alter table DISPOE
   add constraint FK_DISPOE_DISPOE2_USER foreign key (ID_USER)
      references "USER" (ID_USER)
      on delete restrict on update restrict;

alter table GOAL
   add constraint FK_GOAL_AGREGA_HISTORY foreign key (ID_HISTORY)
      references HISTORY (ID_HISTORY)
      on delete restrict on update restrict;

alter table GOAL
   add constraint FK_GOAL_CRIA_USER foreign key (ID_USER)
      references "USER" (ID_USER)
      on delete restrict on update restrict;

alter table GOAL
   add constraint FK_GOAL_PERTENCE_CATEGORY foreign key (ID_CATEGORY)
      references CATEGORY (ID_CATEGORY)
      on delete restrict on update restrict;

alter table HISTORY
   add constraint FK_HISTORY_AGREGA2_GOAL foreign key (ID_GOAL)
      references GOAL (ID_GOAL)
      on delete restrict on update restrict;

alter table HISTORY
   add constraint FK_HISTORY_RECORDA_USER foreign key (ID_USER)
      references "USER" (ID_USER)
      on delete restrict on update restrict;

alter table TROPHY
   add constraint FK_TROPHY_PREMIADO_USER foreign key (ID_USER)
      references "USER" (ID_USER)
      on delete restrict on update restrict;

alter table "USER"
   add constraint FK_USER_CONVERSAR_USER foreign key (USE_ID_USER)
      references "USER" (ID_USER)
      on delete restrict on update restrict;

alter table "USER"
   add constraint FK_USER_TEM_RANK foreign key (ID_RANK)
      references RANK (ID_RANK)
      on delete restrict on update restrict;
      
create sequence sequence_user_seq;
alter table USER alter id_user set default nextval('sequence_user_seq');

create sequence sequence_rank_seq;
alter table RANK alter id_rank set default nextval('sequence_rank_seq');

create sequence sequence_standard_seq;
alter table STANDARD_GOAL alter id_standard set default nextval('sequence_standard_seq');

create sequence sequence_category_seq;
alter table CATEGORY alter id_category set default nextval('sequence_category_seq');

create sequence sequence_goal_seq;
alter table GOAL alter id_goal set default nextval('sequence_goal_seq');


create sequence sequence_history_seq;
alter table HISTORY alter id_history set default nextval('sequence_history_seq');

create sequence sequence_trophy_seq;
alter table TROPHY alter id_throphy set default nextval('sequence_trophy_seq');





