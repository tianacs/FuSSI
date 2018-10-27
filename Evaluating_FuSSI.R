rm (list=ls())    #clear workspace
getwd ()

setwd ("C:/Users/Tiana/Desktop/Project/FuSSI/HCV_Sequences/Output")
output <- read.csv ("Ranked_Likelihoods_Mauger250.csv")
hist (log (output$relative_likelihood_S16), xlab =("log (Relative Likelihood of 16S Model)"), main = "")
sizeHCV <- output[,1]
RL_HCV <- output [,5]
cor.test (sizeHCV, RL_HCV, method = "spearm")


setwd ("C:/Users/Tiana/Desktop/Project/FuSSI/HIV_Sequences//Output/22 Oct run")
outputHIV <- read.csv ("NoND_HIV171.csv")
hist (log (outputHIV$relative_likelihood_S16))
sizeHIV <- outputHIV[,7]
RL_HIV <- outputHIV[,5]
cor.test (sizeHIV, RL_HIV, method = "spearm")

setwd("C:/Users/Tiana/Desktop/Project/FuSSI/HCV_Sequences/Output")
rl <- read.csv ("Graph_Mauger250.csv")
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


setwd("C:/Users/Tiana/Desktop/Project/FuSSI/HIV_Sequences/Output/22 Oct run")
rl <- read.csv ("Graph_HIV171.csv")
library (ggplot2)
ggplot(data=rl, aes(x=rl$Position, y=log(rl$Relative_Likelihood), group = 1))+geom_line(size=1.0)+labs(x="Position in MSA", y="log(Relative Likelihood)")+scale_x_continuous(breaks=seq(0,10000,500))

bio_tested <- c(4.77E+14, 1.17E+61, 3.11E+35,1.00E+57, 3.53E+08, 3.65E+18, 1.63E+34,1,1)
mean(bio_tested)
shuffled <- c(4.57E+15, 6.00E+11, 957.76, 6.40E+19,1,1,1,1,1)
shuffled <- c(1, 1, 1, 1, 1, 3.71E+23, 1, 415386.21, 1)
shuffled <- c(70.98, 485045374, 1.43E+14, 1.59E+09, 3.22E+15,1,1,1,1)
mean (shuffled)
wilcox.test(bio_tested, shuffled)

# Shuffling x10
shuffled1 <- c(9.08E+15, 6.11E+02)
shuffled2 <- c(1,1,1,1,1,1,1,1,1)
shuffled3 <- c(1,1,1,1,1,1,1,1,1)
shuffled4 <- c(2.39E+20, 3.62E+07, 6.97E+04,1,1,1,1,1,1)
shuffled5 <- c(1.45E+06,1,1,1,1,1,1,1,1)
shuffled6 <- c(1,1,1,1,1,1,1,1,1)
shuffled7 <- c(2.87E+07, 1,1,1,1,1,1,1,1)
shuffled8 <- c(1.48E+12,1,1,1,1,1,1,1,1)
shuffled9 <- c(1.08E+15, 2.35E+09, 3.96E+06, 1,1,1,1,1,1)
shuffled10 <- c(6.50E+08, 3.87E+02, 1,1,1,1,1,1,1)
wilcox.test(bio_tested, shuffled1) #0.011
wilcox.test(bio_tested, shuffled2) #0.002
wilcox.test(bio_tested, shuffled3) #0.002
wilcox.test(bio_tested, shuffled4) #0.021
wilcox.test(bio_tested, shuffled5) #0.004
wilcox.test(bio_tested, shuffled6) #0.002
wilcox.test(bio_tested, shuffled7) #0.004
wilcox.test(bio_tested, shuffled8) #0.005
wilcox.test(bio_tested, shuffled9) #0.021
wilcox.test(bio_tested, shuffled10)#0.008
