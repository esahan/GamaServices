create table xxntc.xxntc_maximo_ws_log (
    message_id                  varchar2(50) primary key,
    endpoint                    varchar2(300),
    content_type                varchar2(150),
    content_length              number,
    charset                     varchar2(50),
    operation                   varchar2(100),
    request_method              varchar2(50),
    namespace                   varchar2(300),
    has_error                   varchar2(1),
    request_xml_clob            clob,
    response_xml_clob           clob,
    creation_date               date,
    created_by                  number,
    last_update_date            date,
    last_updated_by             number,
    last_update_login           number        
);

create public synonym xxntc_maximo_ws_log for xxntc.xxntc_maximo_ws_log;