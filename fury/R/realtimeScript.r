print("parser r script start")
source("./R/init.r")
source("./R/initFunction.r")
fury_config <- yaml.load_file("./config/config.yaml")
jobs <- yaml.load_file(fury_config$realtimeJobPath)
connect(es_host = fury_config$valkyrieIp, es_port = fury_config$valkyriePort)
for (idx in 1:length(jobs)){
  new_query <- old_query_parser(paste0("event_time range ", jobs[[idx]]$startDate, "_", jobs[[idx]]$endDate, " & ", jobs[[idx]]$match))
  new_aggs <- old_aggs_parser(jobs[[idx]]$groupBy)
  field_set <- get_field_set(new_aggs)
  query_aggs <- query(new_query) + aggs(new_aggs)
  print(query_aggs)
  tryCatch({
    result <- elastic(paste0("http://",fury_config$valkyrieIp,":",fury_config$valkyriePort), fury_config$valkyrieIndex, fury_config$valkyrieIndexType) %search% query_aggs
    aggregation_insert(jobs[[idx]]$id, jobs[[idx]]$endDate, field_set, result)
    print(paste0(jobs[[idx]]$title, " - success"))
  },
  error = function(e) {
    print(paste0(jobs[[idx]]$title, " - empty_insert [", e, "]"))
    aggregation_empty_insert(jobs[[idx]]$id, jobs[[idx]]$endDate, field_set)
  },
  warning = function(w) print(paste0("w - ", w)),
  finally = NULL)
  #Sys.sleep(fury_config$realtimeIndexingSleep)
}
