package me.mrabar.aml.data.reporting;

import me.mrabar.aml.data.ClientStatus;
import me.mrabar.aml.data.graph.LegalEntity;
import me.mrabar.aml.data.graph.Person;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class ReportsTest {
  @Test
  public void testOwnersReport() {
    OwnersReport or = new OwnersReport(new LegalEntity("1", "Serious business"));
    or.setOwners(List.of(
        new OwnerInfo(BigDecimal.valueOf(45), new Person("123-321", "Max Mustermann", ClientStatus.GREEN)),
        new OwnerInfo(BigDecimal.valueOf(50), new Person("321-222", "Sir Exampleperson", ClientStatus.YELLOW)),
        new OwnerInfo(BigDecimal.valueOf(5), new Person("333-456", "Gospodin Primer",  ClientStatus.RED))
    ));

    System.out.println(or.toString());
  }

  @Test
  public void testPersonReport() {
    PersonReport pr = new PersonReport(new Person("000", "Sus Amongus", ClientStatus.UNKNOWN));
    pr.setShares(List.of(
        new Share(BigDecimal.valueOf(100), new LegalEntity("123321", "Trap Panels Inc")),
        new Share(BigDecimal.valueOf(95), new LegalEntity("6523464", "Guns Ltd")),
        new Share(BigDecimal.valueOf(85), new LegalEntity("12352417", "Murdering Your Crewmates Group"))
    ));

    System.out.println(pr.toString());
  }
}
