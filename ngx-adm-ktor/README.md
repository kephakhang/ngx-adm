# ngx-server

### [개발 및 실행 환경]

- jdk=corretto-11.0.11(AWS openJDK 11)
- gradle_version=7.0.1
- kotlin_plugin_version=1.5.0
- kotlin_version=1.5.0
- kotlinx_version=1.5.0
- ktor_version=1.5.2

### [소스 설명]

- dockerfile : 도커 설정 파일ddd
- src : 코틀린 소스
- resources : 기본 리소스
- resources-$profole : 각 단계별 설정 리소스
- resources/application.conf : 기본 설정 - 자동생성 & 개인설정 추가 가능
- resources/i18n/$lang.conf : 다국어 메세지 설정 파일

<br/>

### [쉘 스크립트 설명]

- build.sh : 빌드 슼립트
- start.sh : 서버 start
- stop.sh : 서버 stop
- docker.sh : 도커 이미지 생성 스크립트 - 작성중
- kafka/kafka.sh : kafka 명령 모음 스크립트

### [DB 스키마 컬럼 <--> JPA Entity]

- DB column : JPA Entity field
- varchar(n) : String
- char(n) : String, ColumDefinition="char(n)
- bigint : Long
- int : Int
- bit : Boolean, ColumnDefinition="bit" : DB 스키마 컬럼 타입에 enum or tinyint 는 사용하지 않는다.
- double : Double
- float : Float
- text, clob : String, ColumDefinition="text" or "clob"
- blob : ByteArray , ColumDefinition="blob"
- datetime : LocalDateTime
- date : LocalDate

### [using Tables]

- tb_continent : Continent Table
- tb_country : Country Table
- tb_call : Http Request Call logging Table
- tb_member : User(Member) Master Table
- tb_member_detail : User(Member) Detail Sub Table
- tb_tenant : Tenant Information Table
- tb_terminal : Terminal V3 Information Table
- tb_counter : Counter V3 Iot Sensor Information Table

### [reserved Tables for the future]

- tb_admin
- tb_agent
- tb_agent_detail
- tb_board
- tb_board_file
- tb_borad_good
- tb_board_new
- tb_board_write
- tb_board_write_detail
- tb_company
- tb_company_member

### licenses

- All of this source is originated and owned by Kepha(kepha@youngplussoft.com)
- This source is under MIT license (https://github.com/peterkhang/ngx-adm/tree/master/ngx-adm-ktor/LICENSE)

<br/>
-  실행 명령 :  ./start.sh

<br/>
-  혹시 소스에 문의사항이 있으시면 저(Kepha)에게 DM  이나 메일 주세요 ~~~
