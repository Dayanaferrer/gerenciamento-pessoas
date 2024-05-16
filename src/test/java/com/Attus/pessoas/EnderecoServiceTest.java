package com.Attus.pessoas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import com.Attus.pessoas.converters.EnderecoConverter;
import com.Attus.pessoas.dtos.EnderecoRecordDto;
import com.Attus.pessoas.models.EnderecoModel;
import com.Attus.pessoas.repositories.EnderecoRepository;
import com.Attus.pessoas.repositories.PessoaRepository;
import com.Attus.pessoas.services.EnderecoService;

@ExtendWith(MockitoExtension.class)
public class EnderecoServiceTest {
	
	  @InjectMocks
	  EnderecoService enderecoService;

	  @Mock
	  EnderecoRepository enderecoRepository;

	  @Mock
	  PessoaRepository pessoaRepository;

	  @Mock
	  EnderecoConverter enderecoConverter;
	   

	   @Test
	   void testSaveEndereco() {
	       EnderecoRecordDto enderecoDto = new EnderecoRecordDto(
	           1L,
	           "Rua Exemplo",
	           "12345-678",
	           "100",
	           "Cidade Exemplo",
	           "Estado Exemplo",
	           true
	       );

	       EnderecoModel enderecoModel = new EnderecoModel();
	       enderecoModel.setId(1L);
	       enderecoModel.setLogradouro("Rua Exemplo");
	       enderecoModel.setCep("12345-678");
	       enderecoModel.setNumero("100");
	       enderecoModel.setCidade("Cidade Exemplo");
	       enderecoModel.setEstado("Estado Exemplo");
	       enderecoModel.setPrincipal(true);

	       when(enderecoConverter.dtoToEntity(enderecoDto)).thenReturn(enderecoModel);
	       when(enderecoRepository.save(enderecoModel)).thenReturn(enderecoModel);
	       when(enderecoConverter.entityToDto(enderecoModel)).thenReturn(enderecoDto);

	       EnderecoRecordDto result = enderecoService.saveEndereco(enderecoDto);

	       assertEquals(enderecoDto, result);
	       verify(enderecoRepository, times(1)).save(enderecoModel);
	   }
	   
	   @Test
	   void testConsultarTodosEnderecos() {
	       EnderecoModel enderecoModel = new EnderecoModel();
	       enderecoModel.setId(1L);
	       enderecoModel.setLogradouro("Rua Exemplo");
	       enderecoModel.setCep("12345-678");
	       enderecoModel.setNumero("100");
	       enderecoModel.setCidade("Cidade Exemplo");
	       enderecoModel.setEstado("Estado Exemplo");
	       enderecoModel.setPrincipal(true);

	       EnderecoRecordDto enderecoDto = new EnderecoRecordDto(
	           1L,
	           "Rua Exemplo",
	           "12345-678",
	           "100",
	           "Cidade Exemplo",
	           "Estado Exemplo",
	           true
	       );

	       when(enderecoRepository.findAll()).thenReturn(Arrays.asList(enderecoModel));
	       when(enderecoConverter.entityToDto(enderecoModel)).thenReturn(enderecoDto);

	       List<EnderecoRecordDto> result = enderecoService.consultarTodosEnderecos();

	       assertEquals(1, result.size());
	       assertEquals(enderecoDto, result.get(0));
	   }
	   
	   @Test
	   void testConsultarEnderecoPorId() {
	       EnderecoModel enderecoModel = new EnderecoModel();
	       enderecoModel.setId(1L);
	       enderecoModel.setLogradouro("Rua Exemplo");
	       enderecoModel.setCep("12345-678");
	       enderecoModel.setNumero("100");
	       enderecoModel.setCidade("Cidade Exemplo");
	       enderecoModel.setEstado("Estado Exemplo");
	       enderecoModel.setPrincipal(true);

	       EnderecoRecordDto enderecoDto = new EnderecoRecordDto(
	           1L,
	           "Rua Exemplo",
	           "12345-678",
	           "100",
	           "Cidade Exemplo",
	           "Estado Exemplo",
	           true
	       );

	       when(enderecoRepository.findById(anyLong())).thenReturn(Optional.of(enderecoModel));
	       when(enderecoConverter.entityToDto(enderecoModel)).thenReturn(enderecoDto);

	       EnderecoRecordDto result = enderecoService.consultarEnderecoPorId(1L);

	       assertEquals(enderecoDto, result);
	   }
	   
	   @Test
	   void testConsultarEnderecoPorIdNotFound() {
	       when(enderecoRepository.findById(anyLong())).thenReturn(Optional.empty());

	       assertThrows(ResponseStatusException.class, () -> enderecoService.consultarEnderecoPorId(1L));
	   }
	   
	   

}
