package tech.deplant.jacki.framework.datatype;

import tech.deplant.commons.Strings;
import tech.deplant.jacki.binding.Boc;
import tech.deplant.jacki.binding.TvmSdk;
import tech.deplant.jacki.binding.TvmSdkException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The type Tvm builder.
 */
public record TvmBuilder(AtomicInteger refCounter, List<Boc.BuilderOp> operations) implements AbiValue {

	/**
	 * Instantiates a new Tvm builder.
	 */
	public TvmBuilder() {
		this(new AtomicInteger(0), new ArrayList<>());
	}

	/**
	 * Builders boc . builder op [ ].
	 *
	 * @return the boc . builder op [ ]
	 */
	public Boc.BuilderOp[] builders() {
		return this.operations.toArray(Boc.BuilderOp[]::new);
	}

	/**
	 * Store tvm builder.
	 *
	 * @param types the types
	 * @return the tvm builder
	 * @throws TvmSdkException the ever sdk exception
	 */
	public TvmBuilder store(AbiValue... types) throws TvmSdkException {
		for (var type : types) {
			this.operations.add(switch (type) {
				case Uint intVal -> new Boc.BuilderOp.Integer((long) intVal.size(), intVal.toABI());
				case Address addr -> new Boc.BuilderOp.Address(addr.toABI());
				case SolString str -> {
					incrementRefCounter();
					yield new Boc.BuilderOp.Cell(new Boc.BuilderOp[]{new Boc.BuilderOp.BitString(Strings.toHexString(str.toABI()))});
				}
				case SolBytes byt -> {
					incrementRefCounter();
					yield new Boc.BuilderOp.Cell(new Boc.BuilderOp[]{new Boc.BuilderOp.BitString(byt.toABI())});
				}
				case TvmBuilder builder -> {
					incrementRefCounter();
					yield new Boc.BuilderOp.Cell(builder.builders());
				}
				default -> throw new TvmSdkException(new TvmSdkException.ErrorResult(-305,
				                                                                       "Builder of TvmCell doesn't support this type for ABI conversion"),
				                                      new Exception());
			});
		}
		return this;
	}

	private void incrementRefCounter() throws TvmSdkException {
		int refCount = this.refCounter.incrementAndGet();
		if (refCount > 4) {
			throw new TvmSdkException(new TvmSdkException.ErrorResult(-306,
			                                                            "TvmCell can't contain more than 4 references to other TvmCells"));
		}
	}

	/**
	 * Store tvm builder.
	 *
	 * @param type       the type
	 * @param inputValue the input value
	 * @return the tvm builder
	 * @throws TvmSdkException the ever sdk exception
	 */
	public TvmBuilder store(AbiType type, Object inputValue) throws TvmSdkException {
		return store(AbiValue.of(type, inputValue));
	}

	/**
	 * To cell tvm cell.
	 *
	 * @param contextId the context id
	 * @return the tvm cell
	 * @throws TvmSdkException the ever sdk exception
	 */
	public TvmCell toCell(int contextId) throws TvmSdkException {
		return new TvmCell(TvmSdk.await(Boc.encodeBoc(contextId, builders(), null)).boc());
	}

	@Override
	public Object toJava() {
		return this;
	}

	@Override
	public Object toABI() {
		return this;
	}

	@Override
	public AbiType type() {
		return new AbiType(AbiTypePrefix.BUILDER, 0, false);
	}
}
