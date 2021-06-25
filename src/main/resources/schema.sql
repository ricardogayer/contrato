create table certificado
(
    certificado bigint not null
        primary key,
    arquivo     varchar(255),
    email       varchar(255),
    enviado     datetime2,
    nome        varchar(255)
)
go