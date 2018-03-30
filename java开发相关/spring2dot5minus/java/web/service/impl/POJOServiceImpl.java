@Service
public class POJOServiceImpl implements POJOService {
	@Autowired
	private POJOMapper pojoMapper;

	@Override
	POJOModel findById(@NotNull Long id) {
		return pojoMapper.selectById(id);
	}
}