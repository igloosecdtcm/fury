print("parser r script start")
source("./R/profile/init.r")
source("./R/profile/initFunction.r")

#fury_config <- yaml.load_file("/home/k/git/fury/fury/config/config.yaml")
fury_config <- yaml.load_file("./config/config.yaml")
tsv_file_list <- dir(fury_config$profileTsvDirPath, pattern = "*.tsv", full.names = TRUE, ignore.case = TRUE)
for (idx in 1:length(tsv_file_list)){
  result <- read.table(file = tsv_file_list[[idx]], sep = '\t', header = FALSE, skip=1)
  print(result)
}