create table "UBER_EATS_GUI"
(
    "STORE_UNIFORM_NO"  number(8, 0)           not null,
    "STORE_UUID"        varchar2(36 byte)      not null,
    "STORE_GUI_TITLE"   varchar2(50 byte)      not null,
    "STORE_NAME"        varchar2(35 byte)      not null,
    "CLIENT_UNIFORM_NO" varchar2(10 byte)      not null,
    "CLIENT_MAIL"       varchar2(40 byte),
    "CLIENT_MODIFY"     varchar2(5 byte)       not null,
    "GUI_NO"            varchar2(10 byte)      not null,
    "ORDER_UUID"        varchar2(39 byte)      not null,
    "WORKFLOW_UUID"     varchar2(39 byte),
    "GUI_DATE"          number(8, 0)           not null,
    "RANDOM_NO"         number(4, 0)           not null,
    "GUI_TAXABLE_AMNT"  number(8, 0)           not null,
    "GUI_VAT_AMNT"      number(8, 0)           not null,
    "GUI_TAXFREE_AMNT"  number(8, 0)           not null,
    "VAT_NO"            number(1, 0)           not null,
    "TOTAL_AMOUNT"      number(8, 0)           not null,
    "TYPE"              number(1, 0)           not null,
    "VOID_DATE"         date,
    "MEMO"              varchar2(4000 byte)    not null,
    "INS_DATE"          date                   not null,
    "FILE_TIME"         number(17, 0)          not null,
    "STATUS"            number(1, 0) default 0 not null,
    "UPDATE_DATE"       date
);

create table "UBER_EATS_GUI_ITEMS"
(
    "ORDER_UUID"   varchar2(39 byte) not null,
    "INS_DATE"     date              not null,
    "GUI_NO"       varchar2(10 byte) not null,
    "TYPE"         number(1, 0)      not null,
    "ITEM_NO"      number(6, 0)      not null,
    "SALES_AMOUNT" number(11, 2)     not null,
    "QTY"          number(5, 0)      not null,
    "TOTAL_PRICE"  number(11, 2)     not null
);