# poST_to_ST
poST translator to Structured Text (ST), based on Xtext/Xtend with Eclipse IDE

# Файлы
Грамматика языка poST : `su.nsk.iae.post/src/su.nsk.iae.post/PoST.xtext`\
Синтаксические проверки : `su.nsk.iae.post/src/su.nsk.iae.post.validation/PoSTValidator.xtext`\
Настройка генераторов : `su.nsk.iae.post/src/su.nsk.iae.post.generator/PoSTGenerator.xtend`\
Генератор в ST : `su.nsk.iae.post/src/su.nsk.iae.post.generator.st.*`\
Генератор в PLCopen XML : `su.nsk.iae.post/src/su.nsk.iae.post.generator.xml.*`

# Установка
1. Для работы с проектом нужна Java 11+.
2. Скачать и установить [Eclipse](https://eclipse.org/downloads/).\
   Проверить что установлена и выбрана Java 11 или выше. "Window" -> "Preferences" -> "Java" -> "Installed JREs".
3. Через меню "Help" -> "Eclipse Marketplace..." установить / обновить:\
  3.1. Eclipse Xtext\
  3.2. Eclipse PDE (Plug-in Development Environment)
4. Скопировать ссылку для скачивания репозитория по протоколу HTTPS:\
   `https://github.com/Vlad264/post_to_st.git`
5. В пустом Workspace Eclipse: "File" -> "Import..." -> "Git" -> "Projects from Git" -> "Clone URI"\
   Вставляем скопированную ссылку. -> "Next -> Из веток выбираем только `dev` (напротив ветки `main` не должно быть галочки). -> Дальше следуем инструкции установки.
6. После установки и сборки проекта открываем пакет: "su.nsk.iae.post/src/su.nsk.iae.post". Нажимаем правой кнопкой мыши на файл "GeneratePoST.mwe2" -> "Run As" -> "MWE2 Workflow".
7. Готово.
