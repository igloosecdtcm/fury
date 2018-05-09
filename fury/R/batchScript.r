print("parser r script start")
source("./R/init.r")
source("./R/initFunction.r")
# test
#source("/home/k/ws2/fury/R/init.r")
#source("/home/k/ws2/fury/R/initFunction.r")
#fury_config <- yaml.load_file("/home/k/ws2/fury/config/config.yaml")
fury_config <- yaml.load_file("./config/config.yaml")
#jobs <- yaml.load_file("/home/k/ws2/fury/R/batch_test_jobs.yaml")
jobs <- yaml.load_file(fury_config$batchJobPath)
connect(es_host = fury_config$valkyrieIp, es_port = fury_config$valkyriePort)
success_count <- 0
empty_count <- 0

for (idx in 1:length(jobs)){
  new_query <- old_query_parser(paste0("event_time range ", jobs[[idx]]$startDate, "_", jobs[[idx]]$endDate, " & ", jobs[[idx]]$match))
  new_aggs <- old_aggs_parser(jobs[[idx]]$groupBy)
  field_set <- get_field_set(new_aggs)
  query_aggs <- query(new_query) + aggs(new_aggs)
  tryCatch({
    result <- elastic(paste0("http://",fury_config$valkyrieIp,":",fury_config$valkyriePort), fury_config$valkyrieIndex, fury_config$valkyrieIndexType) %search% query_aggs
    success_count <- success_count + aggregation_insert(jobs[[idx]]$id, jobs[[idx]]$endDate, field_set, result)
    print(paste0(jobs[[idx]]$title, " - success"))
  },
  error = function(e) {
    print(paste0(jobs[[idx]]$title, " - empty_insert [", e, "]"))
    empty_count <- empty_count + aggregation_empty_insert(jobs[[idx]]$id, jobs[[idx]]$endDate, field_set)
    print(empty_count)
  },
  warning = function(w) print(paste0("w - ", w)),
  finally = NULL)
  Sys.sleep(fury_config$batchIndexingSleep)
}
print(paste0("success_count - ", success_count))
print(paste0("empty_count - ", empty_count))
