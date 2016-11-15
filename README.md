# Incl
Плагин Incl - поможет легко подключать библиотеки к Android Studio проекту. 

## Как пользоваться:

1. Нажмите [SHIFT + CMD(CTRL) + A], откроется окно "Enter action or options name: "
2. Начните набирать одну из команд Incl:
  * Include Dagger2 (Incl)
  * Include Java 8 compatibility (Incl)
  * Include Vkontakte (Incl)
  * Include apt (Incl)
  * Include jitpack (Incl)
  * Include appcompat (Incl)
  * Include DataManager (Incl)
3. Нажмите Enter для выбора команды

Действие "Include Vkontakte" например, добавит все, что необходимо для использования Vkontakte Android SDK, одной командой.

## Преимущества Incl:

 * Использовать Incl гораздо удобнее чем копировать кучу строк в разные места, для каждого проекта. 

 * Incl гибче чем создание базового проекта с набором подключенных библиотек. 

 * Incl проще чем библиотека-обертка с коллекцией подключаемых модулей. 

<b>Incl - это возможность быстро и удобно подключать библиотеки и фреймворки к вашему проекту.</b>

## Как установить:

0. Скачайте [архив с плагином](https://github.com/e16din/Incl/blob/master/Incl.zip).
1. Откройте меню Android Studio > Preferences.
2. Выберите раздел Plugins и нажмите кнопку "Install plugin from disk".
3. Перейдите к папке, в которую скачали плагин и дважды щелкните по нему.
4. Перезагрузите IDE кнопкой Restart Android Studio.

## Как расширять:

1. В папке src/ создайте свой класс с префиксом "Incl" и унаследуйте его от BaseIncl.
```kotlin
class InclApt : BaseIncl() {

    override fun name() = "example"

    override fun include() {
        includeJitpack()
        includeApt()
        
        insertToGradleBlock(GRADLE_BLOCK_DEPENDENCIES, "com.example:library:1.2.3")
        insertToClass(TYPE_APPLICATION, BLOCK_ON_CREATE, "\n        //your code here")
    }
}
```

2. Добавьте action в resources/META-INF/plugin.xml по аналогии с действием Include.vkontakte:
```xml
<actions>
    <action id="Include.vkontakte" class="InclVkontakte" text="Include _Vkontakte (Incl)"
                description="Include Vkontakte SDK"/>
    ...
    <action id="Include.example" class="InclExample" text="Include _example (Incl)"
                description="Example description"/>
</actions>
```

3. В меню Build, нажмите пункт "Prepare Plugin Module 'Incl' For Deployment", что-бы собрать плагин в установочный zip-архив.

## Как будет развиваться проект?

1. Сделать полноценный UI для поиска библиотек в репозиториях jcenter и jitpack.io.
2. Подключать найденные библиотеки подобно тому как это делается в плагинах Fabric и Firebase, с описанием того, что будет сделано. Во время вставки показывать список логов.
3. Сделать файл для управления подключением библиотеки, сформировав такой файл в своем репозитории библиотеки, можно указать какие ресурсы надо подключить помимо самой библиотеки.

## Добавляйте свои библиотеки, заводите issues, делайте pull request'ы!

## License MIT
Copyright (c) 2016 [Александр Кундрюков (e16din)](http://goo.gl/pzjc8x)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
