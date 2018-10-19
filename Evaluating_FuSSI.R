rm (list=ls())    #clear workspace
getwd ()
setwd("/home/tiana/FuSSI/HIV_Sequences/Output/")

things <- read.csv ("Testing_output.csv")
output <- read.csv ("Testing_Mauger250.csv")
size <- things[,7]
rel_like <- things[,5]

sizeO <- output[,7]
RL_O <- output [,5]

cor.test (size, rel_like, method = "spearm")
cor.test (sizeO, RL_O, method = "spearm")

x <- output[,1]


scaled <- things[,7]
cor.test(size, scaled, method = "spearm")

rl <- read.csv ("Graph.csv")
library (ggplot2)
ggplot(data=rl, aes(x=rl$X0, y=log(rl$X0.0), group = 1))+
  labs(x="Position in MSA", y="log(Relative Likelihood)")+
  scale_x_continuous(breaks=seq(0,10000,500))+
  geom_rect (data=NULL, aes (xmin = 101, xmax = 343, ymin=-Inf, ymax=Inf), colour = "grey",fill = "lightgreen", alpha=0.02)+
  geom_rect (data=NULL, aes (xmin = 9511, xmax = 9583, ymin=-Inf, ymax=Inf), colour = "grey", fill = "lightgreen", alpha=0.02)+
  geom_rect (data=NULL, aes (xmin = 9744, xmax = 9791, ymin=-Inf, ymax=Inf), colour = "grey", fill = "lightgreen", alpha=0.02)+
  geom_rect (data=NULL, aes (xmin = 765, xmax = 840, ymin=-Inf, ymax=Inf), colour = "grey", fill = "lightgreen", alpha=0.02)+
  geom_rect (data=NULL, aes (xmin = 8228, xmax = 8398, ymin=-Inf, ymax=Inf), colour = "grey",fill = "lightgreen", alpha=0.02)+
  geom_rect (data=NULL, aes (xmin = 9047, xmax = 9136, ymin=-Inf, ymax=Inf), colour = "grey",fill = "lightgreen", alpha=0.02)+
  geom_rect (data=NULL, aes (xmin = 9293, xmax = 9470, ymin=-Inf, ymax=Inf), colour = "grey",fill = "lightgreen", alpha=0.02)+
  geom_rect (data=NULL, aes (xmin = 402, xmax = 437, ymin=-Inf, ymax=Inf), colour = "grey",fill = "lightgreen", alpha=0.02)+
  geom_rect (data=NULL, aes (xmin = 441, xmax = 521, ymin=-Inf, ymax=Inf),colour = "grey", fill = "lightgreen", alpha=0.02)+
  geom_line(size=1.0)



rl <- read.csv ("Graph_HIV_171.csv")
library (ggplot2)
ggplot(data=rl, aes(x=rl$Position, y=log(rl$Relative_Likelihood), group = 1))+geom_line(size=1.0)+labs(x="Position in MSA", y="log(Relative Likelihood)")+scale_x_continuous(breaks=seq(0,10000,500))

bio_tested <- c(4.77E+14, 1.17E+61, 3.11E+35,1.00E+57, 3.53E+08, 3.65E+18, 1.63E+34,1,1)
mean(bio_tested)
shuffled <- c(4.57E+15, 6.00E+11, 957.76, 6.40E+19,1,1,1,1,1)
shuffled <- c(1, 1, 1, 1, 1, 3.71E+23, 1, 415386.21, 1)
shuffled <- c(70.98, 485045374, 1.43E+14, 1.59E+09, 3.22E+15,1,1,1,1)
mean (shuffled)
wilcox.test(bio_tested, shuffled)
