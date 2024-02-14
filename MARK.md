allure报告生成--终端命令
通过maven命令构建自动化测试
1:mvn clean test
通过maven命令生成allure报告
2:mvn io.qameta.allure:allure-maven:serve

mac系统的路径分隔符是“/”，windows系统的路径分隔符是“\”，所以在写路径的时候，可以使用os.path.join()方法，这样就不用考虑系统的路径分隔符了
'''
在文件路径中，Windows系统通常使用反斜杠（`\`）作为路径分隔符，而Unix和Unix-like系统（如Linux和macOS）则使用正斜杠（`/`）作为路径分隔符。

在Java中，字符串中的反斜杠（`\`）是一个特殊字符，被用作转义字符。例如，`\n`代表一个换行符，`\t`代表一个制表符等。因此，如果你想在字符串中包含一个实际的反斜杠，你需要使用两个反斜杠（`\\`）。所以在Windows系统中，一个文件路径如`C:\path\to\file`在Java字符串中应该被写作`"C:\\path\\to\\file"`。

然而，正斜杠（`/`）在Java字符串中并没有特殊的含义，所以你可以直接在字符串中使用它，无需转义。因此，在Unix和Unix-like系统中，你可以直接写`"/path/to/file"`来表示一个文件路径。

所以，你的代码中应该使用`"/"`作为路径分隔符，因为你正在使用的是macOS系统。如果你的代码需要在Windows系统上运行，你可以使用`File.separator`来代替硬编码的路径分隔符，这样你的代码就可以在任何系统上运行了。
'''
// 拉取的java代码,点击pom.xml右键,选择maven->转为maven项目,代码就正常了