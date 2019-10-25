compiler = javac
compext = *.java
basepath = com/umsl/vasylonufriyev/
main =
datasourceparserpath = DatasourceParser/
datastructurespath = DataStructures/
tokenscannerpath = TokenScanner/
programparserpath = ProgramParser/

buildpath = $(basepath)$(main)$(compext) $(basepath)$(datasourceparserpath)$(compext) $(basepath)$(datastructurespath)$(compext) $(basepath)$(tokenscannerpath)$(compext) $(basepath)$(programparserpath)$(compext)

jarname = frontEnd.jar
manifestpath = ./MANIFEST.MF
jarbuilder = jar cfm $(jarname) $(manifestpath)
buildext = *.class

jarbuildpath = $(basepath)$(main)$(buildext) $(basepath)$(datasourceparserpath)$(buildext) $(basepath)$(datastructurespath)$(buildext) $(basepath)$(tokenscannerpath)$(buildext) $(basepath)$(programparserpath)$(buildext)

all:
	$(compiler) $(buildpath) && \
    $(jarbuilder) $(jarbuildpath)
clean:
	find . -name "*.class" -type f -delete && \
	rm *.jar
clean-output:
	rm *.postorder *.preorder *.inorder
