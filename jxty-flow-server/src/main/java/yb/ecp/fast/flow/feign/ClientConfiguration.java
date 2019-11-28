package yb.ecp.fast.flow.feign;

import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.Feign.Builder;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

@Service
@Import({FeignClientsConfiguration.class})
public class ClientConfiguration
{
  private final Feign.Builder nameBuilder;
  
  @Autowired
  public ClientConfiguration(Decoder decoder, Encoder encoder, Client client, Contract contract)
  {
    this.nameBuilder = Feign.builder().client(client).encoder(encoder).decoder(decoder).contract(contract);
  }
  
  public ServerClient newInstanceByName(String name)
  {
    return (ServerClient)this.nameBuilder.target(ServerClient.class, name);
  }
}
