all : test.class
	fuser -k 55555/tcp || exit 0
	../../mgrep -p 55555 -w word_divider.txt -f dictionary.txt -c CaseFolding.txt
	java -Xss1G -Xmx2G -cp ../../mgrepclient.jar:. test > current
	diff current standard

test.class : test.java
	javac -Xmx2G -cp ../../mgrepclient.jar $<
