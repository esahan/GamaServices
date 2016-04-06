--start header table
create table xxntc.xxntc_ap_maximo_inv_header (
    header_id                   number primary key,
    batch_id                    number,
    vendor_site_id              number,
    invoice_date                date,
    invoice_num                 varchar2(50),
    currency_code               varchar2(3),
    invoice_amount              number,
    terms_id                    number,
    maximo_invoice_number       varchar2(150),
    invoice_type                varchar2(150),
    status                      varchar2(50),
    invoice_id                  number,
    creation_date               date,
    created_by                  number,
    last_update_date            date,
    last_updated_by             number,
    last_update_login           number        
);

create public synonym xxntc_ap_maximo_inv_header for xxntc.xxntc_ap_maximo_inv_header;

create sequence xxntc.xxntc_ap_maximo_inv_header_s start with 1 nocache;

create public synonym xxntc_ap_maximo_inv_header_s for xxntc.xxntc_ap_maximo_inv_header_s;

create sequence xxntc.xxntc_ap_maximo_inv_batch_s start with 1 nocache;

create public synonym xxntc_ap_maximo_inv_batch_s for xxntc.xxntc_ap_maximo_inv_batch_s;
--end header table

--start line table
create table xxntc.xxntc_ap_maximo_inv_line (
    line_id                     number primary key,
    header_id                   number,
    line_type                   varchar2(150),
    line_description            varchar2(240),
    item_code                   varchar2(150),
    amount                      number,
    vat_tax_amount              number,
    vat_tax_code                varchar2(100),
    withholding_tax_code        varchar2(100),
    default_dist_ccid           number,
    po_number                   varchar2(150),
    serial_number               varchar2(35),
    asset_category              varchar2(300),
    quantity_invoice            number,
    creation_date               date,
    created_by                  number,
    last_update_date            date,
    last_updated_by             number,
    last_update_login           number      
);

create public synonym xxntc_ap_maximo_inv_line for xxntc.xxntc_ap_maximo_inv_line;

create sequence xxntc.xxntc_ap_maximo_inv_line_s start with 1 nocache;

create public synonym xxntc_ap_maximo_inv_line_s for xxntc.xxntc_ap_maximo_inv_line_s;
--end line table

--start objects
create or replace type xxntc_ap_maximo_inv_l_obj as object (
    line_type                   varchar2(150),
    line_description            varchar2(240),
    item_code                   varchar2(150),
    amount                      number,
    vat_tax_amount              number,
    vat_tax_code                varchar2(100),
    withholding_tax_code        varchar2(100),
    default_dist_ccid           number,
    po_number                   varchar2(150),
    serial_number               varchar2(35),
    asset_category              varchar2(300),
    quantity_invoice            number
);

create or replace type xxntc_ap_maximo_inv_l_obj_tbl is table of xxntc_ap_maximo_inv_l_obj;


create or replace type xxntc_ap_maximo_inv_h_obj as object (
    vendor_site_id              number,
    invoice_date                date,
    invoice_num                 varchar2(50),
    currency_code               varchar2(3),
    invoice_amount              number,
    terms_id                    number,
    maximo_invoice_number       varchar2(150),
    invoice_type                varchar2(150),
    lines                       xxntc_ap_maximo_inv_l_obj_tbl
);

create or replace type xxntc_ap_maximo_inv_h_obj_tbl is table of xxntc_ap_maximo_inv_h_obj;
--end objects

--start result tables
create table xxntc.xxntc_ap_maximo_inv_result (
    result_id                   number primary key,
    batch_id                    number,
    header_id                   number,
    status                      varchar2(1),
    creation_date               date,
    created_by                  number,
    last_update_date            date,
    last_updated_by             number,
    last_update_login           number      
);

create public synonym xxntc_ap_maximo_inv_result for xxntc.xxntc_ap_maximo_inv_result;

create sequence xxntc.xxntc_ap_maximo_inv_result_s start with 1 nocache;

create public synonym xxntc_ap_maximo_inv_result_s for xxntc.xxntc_ap_maximo_inv_result_s;

create table xxntc.xxntc_ap_maximo_inv_res_det (
    result_detail_id            number primary key,
    result_id                   number,
    error_code                  varchar2(20),
    error_message               varchar2(2000),
    creation_date               date,
    created_by                  number,
    last_update_date            date,
    last_updated_by             number,
    last_update_login           number      
);

create public synonym xxntc_ap_maximo_inv_res_det for xxntc.xxntc_ap_maximo_inv_res_det;

create sequence xxntc.xxntc_ap_maximo_inv_res_det_s start with 1 nocache;

create public synonym xxntc_ap_maximo_inv_res_det_s for xxntc.xxntc_ap_maximo_inv_res_det_s;
--end result tables