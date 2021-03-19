# poST_to_ST
poST translator to Structured Text (ST), based on Xtext/Xtend with Eclipse IDE

# Reference
- Branch [main](https://github.com/Vlad264/post_to_st/tree/main) : sources to install poST Plug-in to Elipse IDE.
- Branch [dev](https://github.com/Vlad264/post_to_st/tree/dev) : project code to clone and work with it.
- JAR file to launch translator via Java 1.8+:\
  https://drive.google.com/file/d/1kqlSMqNmeo2UdqSfczguKCX5LT_wJAQm/view?usp=sharing \
  To launch use: `java - jar post_to_st.jar <File name with ".st" extension>`

# Install
1. Download and install [Eclipse](https://eclipse.org/downloads/).
2. From the Eclipse "Help" menu select "Install New Software...".
3. In the "Available Software" dialog click the "Add..." button.
4. In the "Add Repository" dialog enter the name `post` and the location:
   `https://raw.githubusercontent.com/Vlad264/post_to_st/main/site.xml`
5. In the "Work with" choose "post".
6. Select "PoST Feature".
7. Click "Next" and follow the prompts.

# Uninstall
1. From the Eclipse "Help" menu select "About Eclipse IDE"
2. In the "About Eclipse IDE" dialog click the "Installation Details" button.
3. Find "PoST Feature" and click "Uninstall..." button.
