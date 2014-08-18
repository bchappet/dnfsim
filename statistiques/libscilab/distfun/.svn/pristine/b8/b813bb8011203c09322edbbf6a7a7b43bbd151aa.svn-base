
#
# Copyright (C) 2012 - Michael Baudin
#
# This file must be used under the terms of the CeCILL.
# This source file is licensed as described in the file COPYING, which
# you should have received as part of this distribution.  The terms
# are also available at
# http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
#
#

CC=gcc
#CFLAGS=-W -Wall
CFLAGS=
LDFLAGS=-lm
EXEC=driver.exe
SRC= $(wildcard *.c)
OBJ= $(SRC:.c=.o)

all: $(EXEC)

driver.exe: $(OBJ)
	$(CC) -o $@ $^ $(LDFLAGS)

%.o: %.c
	$(CC) -Wall -g -o $@ -c $< $(CFLAGS)

clean:
	rm *.o

