package cn.itcast.freemarker.test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerTest {
	// 模板 + 数据 = 静态页面
	public static void main(String[] args) throws Exception {
		// 1. 创建freemarker核心对象
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);

		// 2. 给核心对象设置模板所在的位置，就是物理路径
		System.out.println(System.getProperty("user.dir"));
		cfg.setDirectoryForTemplateLoading(new File(System.getProperty("user.dir") + "/src\\ftl"));

		// 3. 使用核心对象获取模板对象，需要的参数就是模板名称
		// 官方推荐模板应该是ftl类型的，但是freemarker模板文件只要是文本内容的即可
		// avi jpg gif docx mp3 xlsx itcast类型都可以
		Template template = cfg.getTemplate("txt.avi");

		// 4. 创建模型数据，官方推荐使用Map
		Map<String, Object> root = new HashMap<>();

		// 入门案例
		String name = "world";
		root.put("name", name);

		// javaBean
		Person person = new Person(1000l, "张三");
		root.put("person", person);

		// List
		List<Person> list = new ArrayList<>();
		list.add(new Person(1l, "暴走萝莉"));
		list.add(new Person(2l, "血手诺克"));
		list.add(new Person(3l, "无敌马德伦"));

		root.put("list", list);

		// Map
		Map<String, Person> map = new HashMap<>();
		map.put("m1", new Person(11l, "诸葛亮"));
		map.put("m2", new Person(12l, "司马懿"));
		map.put("m3", new Person(13l, "司马缸砸光"));

		root.put("map", map);

		// List<Map>
		List<Map<String, Person>> lm = new ArrayList<>();

		Map<String, Person> map1 = new HashMap<>();
		map1.put("m1", new Person(21l, "随便写"));
		map1.put("m2", new Person(22l, "随便吃"));

		Map<String, Person> map2 = new HashMap<>();
		map2.put("m1", new Person(31l, "随便看"));
		map2.put("m2", new Person(32l, "随便嗨"));

		lm.add(map1);
		lm.add(map2);

		root.put("lm", lm);

		// date
		Date date = new Date();

		root.put("date", date);

		// null
		String test = "你好";

		root.put("test", test);

		// 5. 声明输出的Writer对象
		Writer out = new FileWriter(new File("C:/Users/tree/Desktop/test.html"));

		// 6. 使用模板对象输出静态页面（文本文件），
		// 需要两个参数，一个是模型数据，第二参数是输出的Writer
		template.process(root, out);

	}

}