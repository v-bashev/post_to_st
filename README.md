# poST_to_ST
poST language generation module to Structured Text (ST) language and PLCopen XML Exchange format.\
Based on [Xtend](https://www.eclipse.org/xtend/) language and [Xtext](https://www.eclipse.org/Xtext/) framework.\
Created for poST language core.

# Reference
- poST language core: https://github.com/v-bashev/post_core
- Branch [main](https://github.com/v-bashev/post_to_st/tree/main) : project source code.
- Branch [dev](https://github.com/v-bashev/post_to_st/tree/dev) : project source code to clone and work with it.
- Branch [plugin](https://github.com/v-bashev/post_to_st/tree/plugin) : sources to install poST Plug-in to Elipse IDE.
- JAR file to launch translator via Java 1.8+:\
  https://drive.google.com/file/d/1o6hcYSeCWji0WaqGhtoudO4zElAX27TI/view?usp=sharing \
  To launch use: `java -jar post2st.jar <File name with ".post" extension>`

# Install poST Plug-in to Elipse IDE
1. Download and install [Eclipse](https://eclipse.org/downloads/).
2. From the Eclipse "Help" menu select "Install New Software...".
3. In the "Available Software" dialog click the "Add..." button.
4. In the "Add Repository" dialog enter the name `post` and the location:\
   `https://raw.githubusercontent.com/v-bashev/post_to_st/plugin/site.xml`
5. In the "Work with" choose "post".
6. Select "PoST Feature".
7. Click "Next" and follow the prompts.

# Uninstall poST Plug-in from Elipse IDE
1. From the Eclipse "Help" menu select "About Eclipse IDE"
2. In the "About Eclipse IDE" dialog click the "Installation Details" button.
3. Find "PoST Feature" and click "Uninstall..." button.
