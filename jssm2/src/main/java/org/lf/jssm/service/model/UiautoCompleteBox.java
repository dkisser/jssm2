package org.lf.jssm.service.model;
/**
 * 封装easyui-combobox自动补全框数据
 * @author Administrator
 */
public class UiautoCompleteBox {
		private String id;
		private String text;
		private String pym;
		private boolean selected;
		
		public boolean getSelected() {
			return selected;
		}
		public void setSelected(boolean selected) {
			this.selected = selected;
		}
		public String getPym() {
			return pym;
		}
		public void setPym(String pym) {
			this.pym = pym;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		
		public UiautoCompleteBox(){}
		
		public UiautoCompleteBox(String id, String text, String pym,
				boolean selected) {
			super();
			this.id = id;
			this.text = text;
			this.pym = pym;
			this.selected = selected;
		};
		
		
		
		
		
}
