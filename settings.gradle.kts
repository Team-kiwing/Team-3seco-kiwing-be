rootProject.name = "kiwing"

include("data")
include("infra")
include("infra:infra-security")
include("infra:infra-redis")
include("infra:infra-s3")
include("infra:infra-lambda")
include("server")
include("server:api")
