

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rohan.bo.InfoBO;
import com.rohan.model.Info;

@Service
public class InfoProcessor {

	public List<InfoBO> process(List<Info> records){
		List<InfoBO> bos = new ArrayList<InfoBO>();
		//TODO calculate
		return bos;
	}
}
