package cm.ex.delivery.service;

import cm.ex.delivery.entity.BrowseContent;
import cm.ex.delivery.entity.IdHolder;
import cm.ex.delivery.repository.BrowseContentRepository;
import cm.ex.delivery.repository.IdHolderRepository;
import cm.ex.delivery.repository.UserRepository;
import cm.ex.delivery.request.BrowseListDto;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.response.BrowseContentResponse;
import cm.ex.delivery.service.interfaces.BrowseContentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BrowseContentServiceImpl implements BrowseContentService {

    @Autowired
    private BrowseContentRepository browseContentRepository;

    @Autowired
    private IdHolderServiceImpl idHolderService;

    @Autowired
    private IdHolderRepository idHolderRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public BasicResponse createBrowseContent(BrowseListDto browseListDto) {
        BrowseContent browseContent = browseContentRepository.save(new BrowseContent(browseListDto.getTitle(), browseListDto.getType()));
        for (String itemId : browseListDto.getIdList()) {
            addItemToBrowseContent(browseContent.getId().toString(), itemId);
        }
        return BasicResponse.builder().status(true).code(200).message("Browse Content created successfully").build();
    }

    @Override
    public BasicResponse addItemToBrowseContent(String browseContentId, String itemId) {
        Optional<BrowseContent> browseContent = browseContentRepository.findById(Long.valueOf(browseContentId));
        if (browseContent.isEmpty()) throw new NoSuchElementException("Browse content not found");

        idHolderService.addToIdHolder(itemId, browseContent.get().getType(), browseContent.get());
        return BasicResponse.builder().status(true).code(200).message("Item added to Browse Content successfully").build();
    }

    @Override
    public List<BrowseContentResponse> listAllBrowseContentByOrder() {
        List<BrowseContent> browseContentList = browseContentRepository.findAllByContentOrderAsc();
        List<BrowseContentResponse> browseContentResponseList = browseContentList.stream().map(
                browseContent -> {
                    List<IdHolder> idHolderList = idHolderService.listIdHolderByBrowseContentId(String.valueOf(browseContent.getId()));
                    List<String> idList = idHolderList.stream().map(IdHolder::getDataId).toList();

                    BrowseContentResponse browseContentResponse = new BrowseContentResponse();
                    browseContentResponse.setId(browseContent.getId());
                    browseContentResponse.setContentOrder(browseContent.getContentOrder());
                    browseContentResponse.setTitle(browseContent.getTitle());
                    browseContentResponse.setType(browseContent.getType());
                    browseContentResponse.setIds(idList);
                    return browseContentResponse;
                }
        ).toList();

        return browseContentResponseList.isEmpty() ? List.of() : browseContentResponseList;
    }

    @Override
    public BrowseContentResponse getBrowseContentIdResponseById(String id) {

        Optional<BrowseContent> browseContent = browseContentRepository.findById(Long.valueOf(id));
        if (browseContent.isEmpty()) throw new NoSuchElementException("Browse Content Not found");

        BrowseContentResponse browseContentResponse = modelMapper.map(browseContent, BrowseContentResponse.class);
        List<IdHolder> idHolderListByBCId = listIdHolderByBrowseContentId(String.valueOf(browseContent.get().getId()));
        browseContentResponse.setIds(idHolderListByBCId.stream().map(
                idHolder -> {
                    return String.valueOf(idHolder.getId());
                }
        ).toList());

        return browseContentResponse;
    }

    @Override
    public List<BrowseContentResponse> listBrowseContentIdResponse() {

        List<BrowseContent> browseContentList = browseContentRepository.findAll();

        List<BrowseContentResponse> browseContentResponseList = new ArrayList<>();
        for (BrowseContent bc : browseContentList) {
            BrowseContentResponse browseContentResponse = modelMapper.map(bc, BrowseContentResponse.class);
            List<IdHolder> idHolderListByBCId = listIdHolderByBrowseContentId(String.valueOf(bc.getId()));
            browseContentResponse.setIds(idHolderListByBCId.stream().map(
                    idHolder -> {
                        return String.valueOf(idHolder.getId());
                    }
            ).toList());
            browseContentResponseList.add(browseContentResponse);
        }

        return browseContentResponseList.isEmpty() ? List.of() : browseContentResponseList;
    }


    @Override
    public BrowseContent getBrowseContentById(String browseContentId) {
        Optional<BrowseContent> browseContent = browseContentRepository.findById(Long.valueOf(browseContentId));
        return browseContent.orElseGet(BrowseContent::new);
    }

    @Override
    public BrowseContent getBrowseContentByTitle(String title) {
        Optional<BrowseContent> browseContent = browseContentRepository.findByTitle(title);
        return browseContent.orElseGet(BrowseContent::new);
    }

    @Override
    public BasicResponse updateOrder(int currentOrder, int newOrder) {
        return null;
    }

    @Override
    public BasicResponse addBrowseContentItem(String browseContentId, String itemId){

        Optional<BrowseContent> browseContent = browseContentRepository.findById(Long.valueOf(browseContentId));
        if(browseContent.isEmpty()) throw new NoSuchElementException("Browse Content not found");

        idHolderService.addToIdHolder(itemId,"restaurant",browseContent.get());
        return BasicResponse.builder().status(true).code(200).message("Item added to Browse Content successfully").build();
    }

    @Override
    public BasicResponse removeBrowseContentItem(String browseContentId, String itemId) {
        idHolderService.removeByBrowseContentIdAndItemId(browseContentId, itemId);
        return BasicResponse.builder().status(true).code(200).message("Item removed from Browse Content successfully").build();
    }

    @Override
    public BasicResponse removeBrowseContentById(String browseContentId) {
        Optional<BrowseContent> browseContent = browseContentRepository.findById(Long.valueOf(browseContentId));
        if (browseContent.isEmpty()) throw new NoSuchElementException("Browse content not found");

        List<IdHolder> idHolderList = idHolderService.listIdHolderByBrowseContentId(browseContentId);
        idHolderRepository.deleteAll(idHolderList);

        browseContentRepository.delete(browseContent.get());
        return BasicResponse.builder().status(true).code(200).message("Browse Content deleted successfully").build();
    }

    private List<IdHolder> listIdHolderByBrowseContentId(String browseContentId) {
        List<IdHolder> idHolderList = idHolderRepository.findByBrowseContentId_Id(Long.valueOf(browseContentId));
        return idHolderList.isEmpty() ? List.of() : idHolderList;
    }
}
