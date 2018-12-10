package com.sap.cloud.s4hana.examples.commands;

import com.sap.cloud.s4hana.examples.addressmgr.commands.GetSingleBusinessPartnerByIdCommand;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerSelectable;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;
import com.sap.cloud.sdk.testutil.MockUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.URI;
import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class GetSingleBusinessPartnerByIdCommandTest {
    private MockUtil mockUtil;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private BusinessPartnerService service;

    @Before
    public void before(){
        mockUtil = new MockUtil();
        mockUtil.mockDefaults();
        mockUtil.mockDestination("ErpQueryEndpoint", URI.create(""));

        new GetSingleBusinessPartnerByIdCommand(null, "");
    }
    @Test
    public  void testGetOne() throws ODataException{

        String id = String.valueOf("123456");
        BusinessPartner alice = new BusinessPartner();
        alice.setFirstName("Alice");
        alice.setLastName("Miller");

        when(service
                .getBusinessPartnerByKey(id)
                .select(any(BusinessPartnerSelectable.class))
                .execute()
        ).thenReturn(alice);

        final BusinessPartner result = new GetSingleBusinessPartnerByIdCommand(service, id).execute();

        assertEquals("Alice", result.getFirstName());
        assertEquals("Miller", result.getLastName());
    }
}
