package project.common.module.autocompletion;

import org.springframework.beans.factory.annotation.Autowired;

import project.common.extend.BaseAction;

public class AutoCompletionAction extends BaseAction {
	@SuppressWarnings("unused")
	@Autowired
	private AutoCompletionBiz biz;
}