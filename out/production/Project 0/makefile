compiler = javac
compext = *.java
basepath = com/umsl/vasylonufriyev/
main =
datasourceparserpath = DatasourceParser/
datastructurespath = DataStructures/

buildpath = $(basepath)$(main)$(compext) $(basepath)$(datasourceparserpath)$(compext) $(basepath)$(datastructurespath)$(compext)

jarname = P0.jar
manifestpath = ./MANIFEST.MF
jarbuilder = jar cfm $(jarname) $(manifestpath)
buildext = *.class

jarbuildpath = $(basepath)$(main)$(buildext) $(basepath)$(datasourceparserpath)$(buildext) $(basepath)$(datastructurespath)$(buildext)

all:
	$(compiler) $(buildpath) && \
    $(jarbuilder) $(jarbuildpath)
clean:
	find . -name "*.class" -type f -delete && \
	rm *.jar
clean-output:
	rm *.postorder *.preorder *.inorder
