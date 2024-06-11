package com.Attus.pessoas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.Attus.pessoas.converters.PessoaConverter;
import com.Attus.pessoas.dtos.EnderecoRecordDto;
import com.Attus.pessoas.dtos.PessoaRecordDto;
import com.Attus.pessoas.exceptions.PessoaInvalidDataException;
import com.Attus.pessoas.exceptions.PessoaNotFoundException;
import com.Attus.pessoas.models.EnderecoModel;
import com.Attus.pessoas.models.PessoaModel;
import com.Attus.pessoas.repositories.PessoaRepository;
import com.Attus.pessoas.services.PessoaService;

public class PessoaServiceTest {

	    @InjectMocks
	    private PessoaService pessoaService;

	    @Mock
	    private PessoaRepository pessoaRepository;

	    @Mock
	    private PessoaConverter pessoaConverter;

	    @BeforeEach
	    public void init() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    public void testCreatePessoa() {
	        PessoaModel pessoaModel = new PessoaModel();
	        pessoaModel.setNomeCompleto("Nome da Pessoa");
	        pessoaModel.setDataNascimento(LocalDate.of(1990, 1, 1)); 

	        EnderecoModel enderecoPrincipal = new EnderecoModel();
	        enderecoPrincipal.setLogradouro("Rua Principal");
	        enderecoPrincipal.setNumero("123");
	        enderecoPrincipal.setCidade("Cidade");
	        enderecoPrincipal.setEstado("Estado");
	        enderecoPrincipal.setCep("12345-678");
	        enderecoPrincipal.setPrincipal(true);

	        Set<EnderecoModel> enderecos = new HashSet<>();
	        enderecos.add(enderecoPrincipal);
	        pessoaModel.setEnderecos(enderecos);

	        when(pessoaRepository.save(any(PessoaModel.class))).thenReturn(pessoaModel);

	        PessoaModel result = pessoaService.createPessoa(pessoaModel);

	        assertEquals(pessoaModel, result);
	        verify(pessoaRepository, times(1)).save(pessoaModel);
	    }

	    @Test
	    public void testBuscarTodas() {
	        List<PessoaModel> pessoas = Arrays.asList(new PessoaModel());
	        when(pessoaRepository.findAll()).thenReturn(pessoas);

	        List<PessoaRecordDto> result = pessoaService.buscarTodas();

	        assertEquals(pessoas.size(), result.size());
	        verify(pessoaRepository, times(1)).findAll();
	    }

	    @Test
	    public void testGetPessoasByNomeCompleto() {
	        PessoaModel pessoaModel = new PessoaModel();
	        EnderecoRecordDto enderecoPrincipal = new EnderecoRecordDto(1L, "Rua Principal", "CEP", "Número", "Cidade", "Estado", true);
	        List<EnderecoRecordDto> enderecosSecundarios = List.of(new EnderecoRecordDto(2L, "Rua Secundária", "CEP", "Número", "Cidade", "Estado", false));
	        PessoaRecordDto pessoaRecordDto = new PessoaRecordDto(1L, "Nome Completo", LocalDate.now(), enderecoPrincipal, enderecosSecundarios);
	        when(pessoaRepository.findByNomeCompletoContaining("Nome")).thenReturn(Arrays.asList(pessoaModel));
	        when(pessoaConverter.entityToDto(pessoaModel)).thenReturn(pessoaRecordDto);
	        List<PessoaRecordDto> result = pessoaService.getPessoasByNomeCompleto("Nome");
	        assertEquals(1, result.size());
	        assertEquals(pessoaRecordDto, result.get(0));
	    }

	    @Test
	    public void testGetPessoasByNomeCompletoNotFound() {
	        String nomeCompleto = "Nome Completo";
	        when(pessoaRepository.findByNomeCompletoContaining(nomeCompleto)).thenReturn(Collections.emptyList());
	        assertThrows(PessoaNotFoundException.class, () -> {
	            pessoaService.getPessoasByNomeCompleto(nomeCompleto); 
	        });
	    }

	    @Test
	    public void testGetPessoasByIds() {
	        PessoaModel pessoaModel = new PessoaModel();
	        EnderecoRecordDto enderecoPrincipal = new EnderecoRecordDto(1L, "Rua Principal", "CEP", "Número", "Cidade", "Estado", true);
	        List<EnderecoRecordDto> enderecosSecundarios = List.of(new EnderecoRecordDto(2L, "Rua Secundária", "CEP", "Número", "Cidade", "Estado", false));
	        PessoaRecordDto pessoaRecordDto = new PessoaRecordDto(1L, "Nome Completo", LocalDate.now(), enderecoPrincipal, enderecosSecundarios);
	        when(pessoaRepository.findByIdIn(Arrays.asList(1L))).thenReturn(Arrays.asList(pessoaModel));
	        when(pessoaConverter.entityToDto(pessoaModel)).thenReturn(pessoaRecordDto);
	        List<PessoaRecordDto> result = pessoaService.getPessoasByIds(Arrays.asList(1L));
	        assertEquals(1, result.size());
	        assertEquals(pessoaRecordDto, result.get(0));
	    }

	    @Test
	    public void testGetPessoasByIdsNotFound() {
	        when(pessoaRepository.findByIdIn(Arrays.asList(1L))).thenReturn(Collections.emptyList());
	        assertThrows(PessoaNotFoundException.class, () -> pessoaService.getPessoasByIds(Arrays.asList(1L)));
	    }
	    
	    @Test
	    public void testUpdatePessoa() {
	        Long id = 1L;
	        EnderecoRecordDto enderecoPrincipal = new EnderecoRecordDto(1L, "Rua Principal", "CEP", "Número", "Cidade", "Estado", true);
	        List<EnderecoRecordDto> enderecosSecundarios = List.of(new EnderecoRecordDto(2L, "Rua Secundária", "CEP", "Número", "Cidade", "Estado", false));
	        PessoaRecordDto pessoaRecordDto = new PessoaRecordDto(id, "Nome Completo", LocalDate.now(), enderecoPrincipal, enderecosSecundarios);
	        
	        assertNotNull(pessoaRecordDto);
	        assertNotNull(pessoaRecordDto.id());
	        assertNotNull(pessoaRecordDto.nomeCompleto());
	        assertNotNull(pessoaRecordDto.dataNascimento());
	        assertNotNull(pessoaRecordDto.enderecoPrincipal());
	        assertFalse(pessoaRecordDto.enderecosSecundarios().isEmpty());

	        PessoaModel pessoaModelToUpdate = pessoaConverter.dtoToEntity(pessoaRecordDto);
	        assertNotNull(pessoaModelToUpdate); 
	        when(pessoaRepository.findById(id)).thenReturn(Optional.of(pessoaModelToUpdate));
	        when(pessoaRepository.save(pessoaModelToUpdate)).thenReturn(pessoaModelToUpdate);
	        when(pessoaConverter.entityToDto(pessoaModelToUpdate)).thenReturn(pessoaRecordDto);
	        PessoaModel result = pessoaService.updatePessoa(pessoaModelToUpdate);
	        assertEquals(pessoaModelToUpdate, result);
	    }

	    @Test
	    public void testUpdatePessoaNotFound() {
	        Long id = 1L;
	        EnderecoRecordDto enderecoPrincipal = new EnderecoRecordDto(1L, "Rua Principal", "CEP", "Número", "Cidade", "Estado", true);
	        List<EnderecoRecordDto> enderecosSecundarios = List.of(new EnderecoRecordDto(2L, "Rua Secundária", "CEP", "Número", "Cidade", "Estado", false));
	        PessoaRecordDto pessoaRecordDto = new PessoaRecordDto(id, "Nome Completo", LocalDate.now(), enderecoPrincipal, enderecosSecundarios);
	        PessoaModel pessoaModelToUpdate = pessoaConverter.dtoToEntity(pessoaRecordDto);
	        when(pessoaRepository.findById(id)).thenReturn(Optional.empty()); 
	        assertThrows(NoSuchElementException.class, () -> pessoaService.updatePessoa(pessoaModelToUpdate)); 
	    }

}
