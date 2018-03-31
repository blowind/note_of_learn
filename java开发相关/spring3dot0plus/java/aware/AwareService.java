package com.zxf.aware;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service  /*  实现这两个接口，获得Bean名称和资源加载的服务 */
public class AwareService implements BeanNameAware, ResourceLoaderAware {
	private String beanName;
	private ResourceLoader loader;

	/* 这个接口方法是给spring容器去填入loader对象  */
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.loader = resourceLoader;
	}

	/* 这个接口方法是给spring容器去填入bean name  */
	@Override
	public void setBeanName(String name) {
		this.beanName = name;
	}

	public void outputResult() {
		System.out.println("Bean的名称为: " + beanName);
		Resource resource = loader.getResource("classpath:test.txt");
		try{
			System.out.println("ResourceLoader加载的文件内容为: " + IOUtils.toString(resource.getInputStream(), "UTF-8"));
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
