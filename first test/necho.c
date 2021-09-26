#include <stdio.h>
#include <stdlib.h>
int main(int argc, char *argv[])
{
int i;
int j=0;
if(argv[1][0] == '-')
{
	j=atoi(argv[1]+1);
}

for (i = 1; i <= j; i++)
{
	printf("%s%s", argv[2], (i < j) ? " " : "");
}
printf("\n");
return 0;
}
