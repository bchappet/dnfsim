#analyse stats.csv

data = read.table("stats.csv",sep=",",header=T)
summary(data)

n = nrow(data)

errorLength = 500
dt = 0.1

errorTime = (n-errorLength)*dt
lastData = subset(data,data$Time > errorTime)
error = lastData[["error_dist"]]
MSE = mean(error)
RMSE = sqrt(MSE)
range = max(lastData[["output"]]) - min(lastData[["output"]])
NRMSE = RMSE/range
sprintf("NRMSE= %f",NRMSE)
plot(lastData[["target_output"]],type='l',col='green')
lines(lastData[["output"]],type='l',col='red')
