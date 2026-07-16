package com.edteam.backend.producto;

import com.edteam.backend.cliente.ClienteRepository;
import com.edteam.backend.security.AuthContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private static final String DEFAULT_IMAGE = "https://via.placeholder.com/600";

    private final ProductoRepository productoRepository;
    private final ClienteRepository clienteRepository;

    public ProductoController(ProductoRepository productoRepository, ClienteRepository clienteRepository) {
        this.productoRepository = productoRepository;
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    public List<ProductoResponse> list() {
        return productoRepository.findAllByOrderByFechaDesc().stream()
            .map(ProductoResponse::from)
            .toList();
    }

    @GetMapping("/{id}")
    public ProductoResponse getById(@PathVariable Long id) {
        return productoRepository.findById(id)
            .map(ProductoResponse::from)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductoResponse create(@Valid @RequestBody ProductoRequest request, HttpServletRequest httpRequest) {
        String uid = currentUid(httpRequest);

        if (!clienteRepository.existsById(uid)) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Debes completar tu perfil de cliente antes de publicar"
            );
        }

        Producto producto = new Producto();
        producto.setNombre(request.nombre().trim());
        producto.setDescripcion(request.descripcion().trim());
        producto.setPrecio(request.precio());
        producto.setFecha(LocalDateTime.now());
        producto.setImagen(
            request.imagen() == null || request.imagen().isBlank()
                ? DEFAULT_IMAGE
                : request.imagen().trim()
        );
        producto.setVendedorUid(uid);

        return ProductoResponse.from(productoRepository.save(producto));
    }

    private static String currentUid(HttpServletRequest request) {
        Object uid = request.getAttribute(AuthContext.FIREBASE_UID_ATTR);
        if (uid == null || uid.toString().isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No autenticado");
        }
        return uid.toString();
    }
}
