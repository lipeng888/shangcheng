这是一个被包含的模板

//List
<#list list as l >
	${l_index + 41}
	${l.id}
	${l.name}
</#list>