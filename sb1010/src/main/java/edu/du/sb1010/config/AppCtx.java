package edu.du.sb1010.config;

import edu.du.sb1010.spring.MemberPrinter;
import edu.du.sb1010.spring.MemberSummaryPrinter;
import edu.du.sb1010.spring.VersionPrinter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
//@ComponentScan(basePackages = {"spring"}) // 패키지명을 풀네임으로 변경
@ComponentScan(basePackages = {"edu.du.sb1010.spring", "edu.du.sb1010.spring2"}, // spring, spring2 패키지 안 같은 클래스에 컴포넌트를 붙임
		// 서로 같은 타입, 같은 빈 이름을 사용해 충돌, 이 경우 둘 중 하나에 명시적으로 Bean이름 지정 or 아래 스캔대상 제외 사용
		excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,
				classes = {NoProduct.class, ManualBean.class})) // 이 애노테이션이 붙으면 스캔대상에서 제외. (spring2-MemRegService 에 @ManualBean 어노테이션을 붙여 제외됨)
public class AppCtx {

	@Bean
	@Qualifier("printer")
	public MemberPrinter memberPrinter1() {
		return new MemberPrinter();
	}
	
	@Bean
	@Qualifier("summaryPrinter")
	public MemberSummaryPrinter memberPrinter2() {
		return new MemberSummaryPrinter();
	}
	
	@Bean
	public VersionPrinter versionPrinter() {
		VersionPrinter versionPrinter = new VersionPrinter();
		versionPrinter.setMajorVersion(5);
		versionPrinter.setMinorVersion(0);
		return versionPrinter;
	}
}
