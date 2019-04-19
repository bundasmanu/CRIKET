/*==============================================================*/
/* DBMS name:      PostgreSQL 8                                 */
/* Created on:     16/04/2019 17:41:09                          */
/*==============================================================*/


drop index CRIA_FK;

drop index CATEGORY_PK;

drop table CATEGORY;

drop index PERTENCE_FK;

drop index GOAL_PK;

drop table GOAL;

drop index RANK_PK;

drop table RANKING;

drop index STANDARD_GOAL_PK;

drop table STANDARD_GOAL;

drop index PREMIADO_FK;

drop index TROPHY_PK;

drop table TROPHY;

drop index CONVERSAR_FK;

drop index TEM_FK;

drop index USER_PK;

drop table UTILIZADOR;

/*==============================================================*/
/* Table: CATEGORY                                              */
/*==============================================================*/
create table CATEGORY (
   ID_CATEGORY          SERIAL               not null,
   ID_USER              INT4                 not null,
   NOME                 VARCHAR(1024)        not null,
   DESCRIPT             VARCHAR(500)         not null,
   constraint PK_CATEGORY primary key (ID_CATEGORY)
);

/*==============================================================*/
/* Index: CATEGORY_PK                                           */
/*==============================================================*/
create unique index CATEGORY_PK on CATEGORY (
ID_CATEGORY
);

/*==============================================================*/
/* Index: CRIA_FK                                               */
/*==============================================================*/
create  index CRIA_FK on CATEGORY (
ID_USER
);

/*==============================================================*/
/* Table: GOAL                                                  */
/*==============================================================*/
create table GOAL (
   ID_GOAL              SERIAL               not null,
   ID_CATEGORY          INT4                 not null,
   NOME                 VARCHAR(1024)        not null,
   DESCRIPT             VARCHAR(500)         not null,
   FREQUENCY             VARCHAR(500)         not null,
   TIPO                 VARCHAR(1024)        not null,
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
/* Index: PERTENCE_FK                                           */
/*==============================================================*/
create  index PERTENCE_FK on GOAL (
ID_CATEGORY
);

/*==============================================================*/
/* Table: RANKING                                               */
/*==============================================================*/
create table RANKING (
   ID_RANK              INT4                 not null,
   NOME                 VARCHAR(1024)        not null,
   DESCRIPT             VARCHAR(500)         not null,
   MINPOINTS            INT4                 not null,
   constraint PK_RANKING primary key (ID_RANK)
);

/*==============================================================*/
/* Index: RANK_PK                                               */
/*==============================================================*/
create unique index RANK_PK on RANKING (
ID_RANK
);

/*==============================================================*/
/* Table: STANDARD_GOAL                                         */
/*==============================================================*/
create table STANDARD_GOAL (
   ID_STANDARD          SERIAL               not null,
   NOME                 VARCHAR(1024)        not null,
   DESCRIPT             VARCHAR(500)         not null,
   TIPO                 VARCHAR(1024)        not null,
   STATUS               VARCHAR(1024)        not null,
   TOTALVALUE           INT4                 not null,
   FLAG_CLICK_CONTROL   INT4                 not null,
   FLAG_ORDER           INT4                 not null,
   MINWEIGHT            FLOAT8               not null,
   MAXWEIGHT            FLOAT8               not null,
   MINAGE               INT4                 not null,
   MAXAGE               INT4                 not null,
   MINHEIGHT            FLOAT8               not null,
   MAXHEIGHT            FLOAT8               not null,
   GENRE                VARCHAR(500)         not null,
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
   NOME                 VARCHAR(1024)        not null,
   DESCRIPT             VARCHAR(500)         not null,
   VALOR                INT4                 not null,
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
/* Table: UTILIZADOR                                            */
/*==============================================================*/
create table UTILIZADOR (
   ID_USER              SERIAL               not null,
   ID_RANK              INT4                 not null,
   UTI_ID_USER          INT4                 null,
   EMAIL                VARCHAR(1024)        not null,
   PASSWORD             VARCHAR(500)         not null,
   NOME                 VARCHAR(1024)        not null,
   AGE                  DATE                 not null,
   GENRE                VARCHAR(500)         not null,
   constraint PK_UTILIZADOR primary key (ID_USER)
);

/*==============================================================*/
/* Index: USER_PK                                               */
/*==============================================================*/
create unique index USER_PK on UTILIZADOR (
ID_USER
);

/*==============================================================*/
/* Index: TEM_FK                                                */
/*==============================================================*/
create  index TEM_FK on UTILIZADOR (
ID_RANK
);

/*==============================================================*/
/* Index: CONVERSAR_FK                                          */
/*==============================================================*/
create  index CONVERSAR_FK on UTILIZADOR (
UTI_ID_USER
);

alter table CATEGORY
   add constraint FK_CATEGORY_CRIA_UTILIZAD foreign key (ID_USER)
      references UTILIZADOR (ID_USER)
      on delete restrict on update restrict;

alter table GOAL
   add constraint FK_GOAL_PERTENCE_CATEGORY foreign key (ID_CATEGORY)
      references CATEGORY (ID_CATEGORY)
      on delete restrict on update restrict;

alter table TROPHY
   add constraint FK_TROPHY_PREMIADO_UTILIZAD foreign key (ID_USER)
      references UTILIZADOR (ID_USER)
      on delete restrict on update restrict;

alter table UTILIZADOR
   add constraint FK_UTILIZAD_CONVERSAR_UTILIZAD foreign key (UTI_ID_USER)
      references UTILIZADOR (ID_USER)
      on delete restrict on update restrict;

alter table UTILIZADOR
   add constraint FK_UTILIZAD_TEM_RANKING foreign key (ID_RANK)
      references RANKING (ID_RANK)
      on delete restrict on update restrict;

create sequence sequence_UTILIZADOR_seq;
alter table UTILIZADOR alter id_UTILIZADOR set default nextval('sequence_UTILIZADOR_seq');

create sequence sequence_rank_seq;
alter table RANKING alter id_rank set default nextval('sequence_rank_seq');

create sequence sequence_standard_seq;
alter table STANDARD_GOAL alter id_standard set default nextval('sequence_standard_seq');

create sequence sequence_category_seq;
alter table CATEGORY alter id_category set default nextval('sequence_category_seq');

create sequence sequence_goal_seq;
alter table GOAL alter id_goal set default nextval('sequence_goal_seq');


create sequence sequence_trophy_seq;
alter table TROPHY alter ID_TROPHY set default nextval('sequence_trophy_seq');

