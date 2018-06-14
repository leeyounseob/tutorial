// 언어를 바꾸는 클래스
// 바로 바뀌지는 않고 한번 다른걸 클릭 해야함
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JLabel;

class SwapLanguage implements ItemListener {
		JLabel subTitle;
		String title[];
		
		SwapLanguage(JLabel subTitle) {
			this.subTitle = subTitle;
		}
		SwapLanguage(String title[]) {
			this.title = title;
		}
		
		
		@Override
		public void itemStateChanged(ItemEvent arg0) {
			String lan = (String)arg0.getItem();
			if(lan.equals("English")) {
				subTitle.setText("File Explorer");
				ATable.title[0] = "Name";
				ATable.title[1] = "Size";
				ATable.title[2] = "Modified";
			}
			else if(lan.equals("한국어")) {
				subTitle.setText("파일 탐색기");
				ATable.title[0] = "이름";
				ATable.title[1] = "크기";
				ATable.title[2] = "수정한 날짜";	
			}
		   
		}
}

