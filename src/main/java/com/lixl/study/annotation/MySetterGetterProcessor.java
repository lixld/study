/*
package com.lixl.study.annotation;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes("com.lixl.study.annotation.MyGetterSetter")
@SupportedSourceVersion(SourceVersion.RELEASE_8)

*/
/**
 * 文章地址：https://mp.weixin.qq.com/s/BZzHAZcEn4T5rXrNKeb5Pw
 *
 * 此类需要在openJDK下才能运行----目前本地的jdk跑不起来
 *
 *
 * 父类AbstractProecessor，编译器在编译时会扫描此类的子类
 * 必须实现process方法、
 * 返回true代表编译的类抽象树结构有变，需要重新词法分析和语法分析
 * 如果返回false 说明没有变化
 *
 *
 * process方法操作逻辑
 * 1.拿到被注解@MyGetterSetter标注的类
 * 2.遍历所有类，生产类的抽象树结构
 * 3.对类进行操作
 *  3.1 找到类所有变量
 *  3.2 对变量生成get/set方法
 * 4.返回true[说明结构变了，需要重新解析]
 *
 *
 *
 *
 * 操作JCTree树
 *
 *
 *//*

public class MySetterGetterProcessor extends AbstractProcessor {
    // 主要是输出信息
    private Messager messager;
    private JavacTrees javacTrees;

    private TreeMaker treeMaker;
    private Names names;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
        this.javacTrees = JavacTrees.instance(processingEnv);
        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        this.treeMaker = TreeMaker.instance(context);
        this.names = Names.instance(context);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // 拿到被注解标注的所有的类
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(MySetterGetter.class);
        elementsAnnotatedWith.forEach(element -> {
            // 得到类的抽象树结构
            JCTree tree = javacTrees.getTree(element);
            // 遍历类，对类进行修改
            tree.accept(new TreeTranslator() {
                @Override
                public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
                    List<JCTree.JCVariableDecl> jcVariableDeclList = List.nil();
                    // 在抽象树中找出所有的变量
                    for (JCTree jcTree : jcClassDecl.defs) {
                        if (jcTree.getKind().equals(Tree.Kind.VARIABLE)) {
                            JCTree.JCVariableDecl jcVariableDecl = (JCTree.JCVariableDecl) jcTree;
                            jcVariableDeclList = jcVariableDeclList.append(jcVariableDecl);
                        }
                    }

                    // 对于变量进行生成方法的操作
                    for (JCTree.JCVariableDecl jcVariableDecl : jcVariableDeclList) {
                        messager.printMessage(Diagnostic.Kind.NOTE, jcVariableDecl.getName() + " has been processed");
                        jcClassDecl.defs = jcClassDecl.defs.prepend(makeSetterMethodDecl(jcVariableDecl));

                        jcClassDecl.defs = jcClassDecl.defs.prepend(makeGetterMethodDecl(jcVariableDecl));
                    }


                    // 生成返回对象
                    JCTree.JCExpression methodType = treeMaker.Type(new Type.JCVoidType());

                    return treeMaker.MethodDef(treeMaker.Modifiers(Flags.PUBLIC), getNewSetterMethodName(jcVariableDecl.getName()), methodType, List.nil(), parameters, List.nil(), block, null);
                }

                */
/**
                 * 生成 getter 方法
                 * @param jcVariableDecl
                 * @return
                 *//*

                private JCTree.JCMethodDecl makeGetterMethodDecl(JCTree.JCVariableDecl jcVariableDecl) {
                    ListBuffer<JCTree.JCStatement> statements = new ListBuffer<>();
                    // 生成表达式
                    JCTree.JCReturn aReturn = treeMaker.Return(treeMaker.Ident(jcVariableDecl.getName()));
                    statements.append(aReturn);
                    JCTree.JCBlock block = treeMaker.Block(0, statements.toList());
                    // 无入参
                    // 生成返回对象
                    JCTree.JCExpression returnType = treeMaker.Type(jcVariableDecl.getType().type);
                    return treeMaker.MethodDef(treeMaker.Modifiers(Flags.PUBLIC), getNewGetterMethodName(jcVariableDecl.getName()), returnType, List.nil(), List.nil(), List.nil(), block, null);
                }

                */
/**
                 * 拼装Setter方法名称字符串
                 * @param name
                 * @return
                 *//*

                private Name getNewSetterMethodName(Name name) {
                    String s = name.toString();
                    return names.fromString("set" + s.substring(0, 1).toUpperCase() + s.substring(1, name.length()));
                }

                */
/**
                 * 拼装 Getter 方法名称的字符串
                 * @param name
                 * @return
                 *//*

                private Name getNewGetterMethodName(Name name) {
                    String s = name.toString();
                    return names.fromString("get" + s.substring(0, 1).toUpperCase() + s.substring(1, name.length()));
                }

                */
/**
                 * 生成表达式
                 * @param lhs
                 * @param rhs
                 * @return
                 *//*

                private JCTree.JCExpressionStatement makeAssignment(JCTree.JCExpression lhs, JCTree.JCExpression rhs) {
                    return treeMaker.Exec(
                            treeMaker.Assign(lhs, rhs)
                    );
                }
            })
        }
    }


    */
/**
     * 生成 getter 方法
     *
     * @param jcVariableDecl
     * @return
     *//*

    private JCTree.JCMethodDecl makeGetterMethodDecl(JCTree.JCVariableDecl jcVariableDecl) {
        ListBuffer<JCTree.JCStatement> statements = new ListBuffer<>();
        // 生成表达式
        JCTree.JCReturn aReturn = treeMaker.Return(treeMaker.Ident(jcVariableDecl.getName()));
        statements.append(aReturn);
        JCTree.JCBlock block = treeMaker.Block(0, statements.toList());
        // 无入参
        // 生成返回对象
        JCTree.JCExpression returnType = treeMaker.Type(jcVariableDecl.getType().type);
        return treeMaker.MethodDef(treeMaker.Modifiers(Flags.PUBLIC), getNewGetterMethodName(jcVariableDecl.getName()), returnType, List.nil(), List.nil(), List.nil(), block, null);
    }

    */
/**
     * 拼装Setter方法名称字符串
     *
     * @param name
     * @return
     *//*

    private Name getNewSetterMethodName(Name name) {
        String s = name.toString();
        return names.fromString("set" + s.substring(0, 1).toUpperCase() + s.substring(1, name.length()));
    }

    */
/**
     * 拼装 Getter 方法名称的字符串
     *
     * @param name
     * @return
     *//*

    private Name getNewGetterMethodName(Name name) {
        String s = name.toString();
        return names.fromString("get" + s.substring(0, 1).toUpperCase() + s.substring(1, name.length()));
    }

    */
/**
     * 生成表达式
     *
     * @param lhs
     * @param rhs
     * @return
     *//*

    private JCTree.JCExpressionStatement makeAssignment(JCTree.JCExpression lhs, JCTree.JCExpression rhs) {
        return treeMaker.Exec(
                treeMaker.Assign(lhs, rhs)
        );
    }

}*/
